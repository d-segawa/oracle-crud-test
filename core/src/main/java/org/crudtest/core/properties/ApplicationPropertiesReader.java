package org.crudtest.core.properties;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ApplicationPropertiesReader {

    static Properties prop = load();

    /**
     * プロパティを以下の優先度で読み込み、マージして返す。
     * 同一キーが存在する場合、優先度の高いソースの値で上書きされる。
     *
     * <ol>
     *   <li>システムプロパティ（{@code System.getProperty(key)}） ― 最優先</li>
     *   <li>外部プロパティファイル（{@code System.getProperty("crud-test.properties.path")} で指定されたパス）</li>
     *   <li>クラスパス上の {@code crud-test.properties} ― 最低優先</li>
     * </ol>
     *
     * @return マージ済みの {@link Properties}
     */
    static Properties load() {
        Properties result = new Properties();

        // (3) クラスパスの crud-test.properties を基底として読み込む
        try (InputStream is = ClassLoader.getSystemResourceAsStream("crud-test.properties")) {
            if (is != null) {
                result.load(is);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file from classpath.", e);
        }

        // (2) 外部ファイルパスが指定されていれば読み込み、クラスパスの値を上書きする
        String propertiesPath = System.getProperty("crud-test.properties.path");
        if (propertiesPath != null) {
            Path path = Paths.get(propertiesPath);
            Properties externalProps = new Properties();
            try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
                externalProps.load(br);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load properties file for crud-test.properties.path.", e);
            }
            result.putAll(externalProps);
        }

        // (1) 各キーに対応するシステムプロパティが存在すれば最優先で上書きする
        result.keySet().forEach(key -> {
            String value = System.getProperty((String) key);
            if (value != null) {
                result.put(key, value);
            }
        });

        return result;
    }

}
