# if you want to commit large file (file have more than 50MB), install git lfs please
# 1 - download git-lfs in git-lfs-1.1.0 folder
# 2 - cd to git-lfs-1.1.0, run this command
sudo ./install.sh
# 3 - config for large file, ex if you want to commit file.psd that has more than 50 MB
git lfs track "*.psd"
# 4 - run 3 step normal as below description
git add file.psd
git commit -m "Add design file"
git push origin master
