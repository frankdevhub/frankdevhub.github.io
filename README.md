# frankdevhub.github.io
Blog template powered by jekyll

# jekyll搭建博客

## 1. jekyll介绍
   `Jekyll`是一个简单的，博客感知的静态站点生成器。
   你将内容创建为文本文件（Markdown），并将其放到到文件夹中。然后，使用`Liquid-enhanced HTML`模板构建网站。Jekyll自动将内容和模板联系在一起，生成完全由静态资源组成的网站，它适合上传到任何服务器。
   Jekyll恰好是`GitHub Pages`的引擎，因此你可以在GitHub的服务器上免费托管项目的Jekyll页面/博客/网站。

## 2. 安装
   ### 2.1 安装 Ruby development environment
   [`windows`安装教程](https://jekyllrb.com/docs/installation/windows/).
   ### 2.2 安装Jekyll and bundler gems.

```shell
# 移除gem默认源，改成ruby-china源
$ gem sources -r https://rubygems.org/ -a https://gems.ruby-china.com/
# 使用Gemfile和Bundle的项目，可以做下面修改，就不用修改Gemfile的source
$ bundle config mirror.https://rubygems.org https://gems.ruby-china.com
# 删除Bundle的一个镜像源
$ bundle config --delete 'mirror.https://rubygems.org'
$ gem install jekyll bundler

```

