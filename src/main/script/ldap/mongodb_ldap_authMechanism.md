# 手動安裝 LDAP 
###### 參考文件 :
###### https://blog.twjoin.com/%E7%AD%86%E8%A8%98-openldap-%E5%BB%BA%E7%BD%AE%E8%88%87%E4%BB%8B%E6%8E%A5-3e16175b6c3c

---
install package

    sudo apt-get install ldap-utils slapd

enable service

    sudo systemctl enable slapd.service
    sudo systemctl start slapd.service
    sudo systemctl status slapd.service

配置預設項目\
這裡使用 cn=admin,dc=l-penguin,dc=idv,dc=tw

    sudo dpkg-reconfigure slapd

check service

    sudo slapcat
    netstat -tpln

config file

    sudo find / -name slapd.conf
    vi /usr/share/doc/slapd/examples/slapd.conf

test ldap connection

    # find all user
    ldapsearch -x -H ldap://192.168.56.133:389 -b "dc=l-penguin,dc=idv,dc=tw" "(objectClass=posixAccount)"
    # find user
    ldapsearch -x -H ldap://192.168.56.133:389 -b "dc=l-penguin,dc=idv,dc=tw" "(uid=teddylai)" -D "cn=admin,dc=l-penguin,dc=idv,dc=tw" -W
    # find user by dn
    ldapsearch -x -H ldap://192.168.56.133:389 -b "uid=teddylai,ou=systex,dc=l-penguin,dc=idv,dc=tw" -D "cn=admin,dc=l-penguin,dc=idv,dc=tw" -W

這裡使用 LDAP Admin （ windows 客戶端 ）連接與新增修改 user \
Softerra LDAP Administrator 要錢 = =
    
     do something here ...

mongodb connection string with ldap sample （需要先在 $external db 建好對應user）\
連線字串轉義 %40 == @  , %24 == $ , 可以用這個網址轉義 encodeURIComponent()
https://www.sojson.com/encodeurl.html

    mongosh "mongodb://cn=admin,dc=l-penguin,dc=idv,dc=tw:1qaz%40WSX@poc11.mongo.com:27017,poc22.mongo.com:27017,poc33.mongo.com:27017/test?authSource=%24external&replicaSet=myReplicaSetRemote&tls=true&tlsCAFile=/etc/ssl/server.pem&tlsCertificateKeyFile=/etc/ssl/server.pem&authMechanism=PLAIN"
    mongosh "mongodb://uid=teddylai,ou=systex,dc=l-penguin,dc=idv,dc=tw:1qaz%40WSX@poc11.mongo.com:27017,poc22.mongo.com:27017,poc33.mongo.com:27017/demo?authSource=%24external&replicaSet=myReplicaSetRemote&tls=true&tlsCAFile=/etc/ssl/server.pem&tlsCertificateKeyFile=/etc/ssl/server.pem&authMechanism=PLAIN"
