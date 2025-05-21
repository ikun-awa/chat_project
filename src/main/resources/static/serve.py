from livereload import Server

server = Server()
# 监视文件改动
server.watch('wocao/*.html')    # 支持 glob 模式，监控所有 HTML/CSS/JS 文件&#8203;:contentReference[oaicite:3]{index=3}
server.watch('wocao/*.css')
server.watch('wocao/*.js')

# 启动静态服务器在 8000 端口，并在 35729 端口开启 livereload 服务
server.serve(root='.', port=8000, liveport=35729)

