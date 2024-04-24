# 用 docker 起一個 linux service
###### 參考文件 :
###### https://blog.puckwang.com/posts/2022/use-docker-run-ldap-server/

---
建立一個 LDAP Server 所有設定為預設值。

    docker run --name ldap-server  --hostname ldap-server \
    -p 389:389 -p 636:636  --detach  osixia/openldap:latest

建立一個 phpLDAPadmi－n，所有設定為預設值。\
訪問網頁範例 , domain 為 docker 宿主機
###### https://192.168.56.133:6443/cmd.php?server_id=1&redirect=true
###### http://192.168.56.133:8087/cmd.php?server_id=1&redirect=true

    # 開啟 https (有自簽憑證)
    docker run --name ldap-admin -p 6443:443 --link ldap-server:ldap-host \
    --env PHPLDAPADMIN_LDAP_HOSTS=ldap-host --detach osixia/phpldapadmin:latest

    # PHPLDAPADMIN_HTTPS=false == 關閉 https
    docker run --name ldap-admin -p 8087:80 --link ldap-server:ldap-host  \
    --env PHPLDAPADMIN_LDAP_HOSTS=ldap-host --env PHPLDAPADMIN_HTTPS=false --detach  osixia/phpldapadmin:latest

啟動服務 , 可加在 rc.local 

    docker start ldap-server
    docker start ldap-admin 

進入 docker terminal

    docker exec -i -t ldap-server bash
    docker exec -i -t ldap-admin  bash


---
# 其他參考指令

停止所有容器运行：

    docker stop $(docker ps -a -q)

删除所有停止运行的容器：

    docker rm $(docker ps -a -q)

檢查 docker 狀態：

    systemctl status docker

---
# 測試 ldap 連線
    sudo apt-get install slapd ldap-utils
    sudo yum install openldap openldap-clients

    ldapsearch -x -H ldap://192.168.56.133:636 -b "cn=admin,dc=example,dc=org" -D "cn=admin,dc=example,dc=org" -W
    ldapsearch -x -H ldap://192.168.56.133:389 -b "cn=teddylai,cn=fubon,ou=mongodb,dc=example,dc=org" -D "cn=teddylai,cn=fubon,ou=mongodb,dc=example,dc=org" -W
