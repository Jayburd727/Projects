# Backend

## Dokku Link
http://2023sp-group17.dokku.cse.lehigh.edu

## ElephantSQL
Database URL: postgres://ozhubnpc:***@castor.db.elephantsql.com/ozhubnpc

Note *** is for password

## Run of Dokku (Expected)
1. Do any Updates and then add/commit updates
2. git push dokku backend-dokku:master
3. http://2023sp-group17.dokku.cse.lehigh.edu/messages (Current Main Page)

## Run Locally (Expected)
1. Open a Git Bash Terminal
2. Type and run the following

```
mvn clean
PORT=8998 POSTGRES_IP=castor.db.elephantsql.com POSTGRES_PORT=5432 POSTGRES_USER=ozhubnpc POSTGRES_PASS=*** mvn package
PORT=8998 POSTGRES_IP=castor.db.elephantsql.com POSTGRES_PORT=5432 POSTGRES_USER=ozhubnpc POSTGRES_PASS=*** mvn exec:java
```

3. Once again note *** mean the password

## Javadocs Link
https://drive.google.com/drive/folders/0ABI8lu0dvsZqUk9PVA
file:///Users/mackenziekramer/Desktop/CSE216/buzzrepo/backend/target/site/apidocs/allclasses-index.html 