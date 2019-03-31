

echo "------------- jekyll build start -------------"


echo ""------------- jekyll build complete "-------------"


echo "------------- cd into site package ------------"
echo "------------- change 'localhost:4000' to website host -------------"


echo "------------- change complete -------------"


echo "------------- move sitemap.xml and robot.txt to  main root -------------"


echo "------------- push git add ,git commit ,git push-------------"

git add .
git commit -m "generate new sitemap"
git push -u origin master

echo "------------- sitemap created successfully -------------"



pause