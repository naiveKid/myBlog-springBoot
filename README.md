# myBlog
# 使用了redis、mongodb、mysql. #

- 建库命令：存放在sql文件夹的sql文件中




## windows下安装reids ##
1.redis下载地址：https://github.com/MSOpenTech/redis/releases。

2.启动redis服务：在redis目录下启动DOS命令，然后执行redis-server.exe

3.另启一个cmd窗口，原来的不要关闭，不然就无法访问服务端了。

4.切换到redis目录下运行 redis-cli.exe -h 127.0.0.1 -p 6379 。

相关命令：

> 设置键值对 set myKey abc
> 
> 取出键值对 get myKey


## 设置redis为windows自启服务 ##

Redis是可以安装成windows服务的，开机自启动，命令如下：

redis-server --service-install redis.windows.conf

安装好之后，Redis并没有启动，启动命令如下：

    redis-server --service-start

停止命令：

    redis-server --service-stop

## Windows下安装mongodb ##

1、下载

MongoDB的官网是：http://www.mongodb.org/

下载地址，http://dl.mongodb.org/dl/win32/x86_64

2、安装

2.1、创建一个目录用于安装数据库，如D:\ mongodb

2.2、在D:\mongodb下解压下载的zip文件，然后在D:\mongod下新建data文件夹

2.3、然后在data文件夹下面创建db和log文件夹
  

2.4、在log文件夹下创建一个日志文件mongodb.log

2.5、管理员身份运行cmd进入到D:\mongodb\bin，然后执行命令：mongod -dbpath "D:\mongodb\data\db"

2.6、安装完成后浏览器里面输入http://127.0.0.1:27017/，如果看到的页面内容如下，表示安装成功

3、注册服务

3.1、在 D:\mongodb文件夹下新建文件mongo.config，输入以下内容：

    dbpath=D:\mongodb\data\db logpath=D:\mongodb\data\log\mongodb.log



3.2、以管理员身份证打开cmd命令，然后进入到D:\mongodb\bin位置，分别执行以下两条命令：

    mongod --bind_ip 127.0.0.1 --config D:\mongodb\mongo.config --install --serviceName "MongoDB" --journal
    mongod --auth --config D:\mongodb\mongo.config --install --serviceName "MongoDB" –journal
    

至此，数据库的安装就全部ok完毕了，现在就去服务里面把数据库服务启动


4、创建数据库

4.1、cmd进入mongodb的bin目录下面，执行mongo.exe ip

4.2、执行命令建库

   ` use myBlog`

如果数据库不存在，则创建数据库，否则切换到指定数据库。

4.3、执行命令创建数据库用户名和密码，用户名和密码也与数据库名称保持一致

    db.createUser({
      user: "myBlog", 
      pwd: "myBlog", 
      roles: [ { 
        role: "readWrite", db: "myBlog" 
      } ] 
    })

4.4、验证用户，`db.auth('myBlog','myBlog')`输出1表示验证成功


## Windows下安装nginx ##
1.下载nginx windows 安装包

2.解压，修改配置：

打开/conf/nginx.conf:

    worker_processes  1;
    
    events {
       worker_connections  1024;
    }
    
    
    http {
       include   mime.types;
       default_type  application/octet-stream;
    
       sendfileon;
    
       keepalive_timeout  65;
    
       server {
          #nginx启动端口号
          listen   8888;
          server_name  localhost;
    	  charset utf-8;

          location / {
             root   html;
             index  index.html index.htm;
          }
          #将d:/filePath下的文件目录映射到url http//:localhost:8888/images
          location /images {
             alias d:/filePath;
          }
    
          error_page   500 502 503 504  /50x.html;
          location = /50x.html {
             root   html;
          }
       }
    }
    
3.执行nginx.exe即可。