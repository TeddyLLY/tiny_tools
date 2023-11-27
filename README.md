#設定檔讀取路徑 ＆＆ log 輸出路徑為 jar 當前目錄\
oracle_config.properties 記得移到跟打包後的 jar 同一路徑

# 打包 jar 指令
    mvn clean package

# 切 root 執行 class 指令
    java -cp tiny_tools-1.0-SNAPSHOT.jar com.tools.OracleJDBC