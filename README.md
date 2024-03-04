### 1. 在 ORACLE 機器上執行 java class
    # 相關連線資訊由外部取得
    # 開啟 multi threads 
    執行指定 sql 最後紀錄執行開始與完成時間
    
    #設定檔讀取路徑 ＆＆ log 輸出路徑為 jar 當前目錄\
    oracle_config.properties 記得移到跟打包後的 jar 同一路徑

### 2. 從 Excel 讀取並輸出 insert 語法

# 打包 jar 指令
    mvn clean package

# 切 root 執行 class 指令範例
    java -cp tiny_tools-1.0-SNAPSHOT.jar com.tools.OracleJDBC

