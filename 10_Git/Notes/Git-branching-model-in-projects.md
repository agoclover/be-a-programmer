# Background

删除.git文件夹可能会导致git存储库中的问题。如果要删除所有提交历史记录，但将代码保持在当前状态，可以按照以下方式安全地执行此操作：

```shell
尝试  运行  git checkout --orphan latest_branch
添加所有文件git add -A
提交更改git commit -am "commit message"
删除分支git branch -D master
将当前分支重命名git branch -m master
最后，强制更新存储库。git push -f origin master
```



# References

1. [A successful Git branching model](https://nvie.com/posts/a-successful-git-branching-model/)
2. [Understanding the GitHub flow](https://guides.github.com/introduction/flow/)
3. [实际项目中如何使用Git做分支管理](https://zhuanlan.zhihu.com/p/38772378)
4. [开眼了，腾讯是如何使用 Git？](https://zhuanlan.zhihu.com/p/143941172)
5. [Git summary blog article](http://www.mtmn.top/archives/git)

