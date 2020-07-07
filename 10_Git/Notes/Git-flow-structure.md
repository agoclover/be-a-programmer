# For work
## Branches

- master
- develop
- feature branches
- release branches
- hotfix branches

## commit type
type 是用于说明该 commit 的类型的, 一般我们会规定 type 的类型如下:

- feat 新特性 feature
- fix 修改 but
- refactor 代码重构. 既不是新增功能, 也不是修改 bug 的代码变动
- docs 文档修改 documents
- style 代码格式修改, 不影响代码运行的格式变动, 注意不是指 CSS 的修改
- test 提交测试代码 (单元测试，集成测试等)
- chore 其他修改, 比如构建流程, 依赖管理.
- misc 一些未归类或不知道将它归类到什么方面的提交

## commit scope 
scope commit 影响的范围, 比如数据层, 控制层, 视图层等等, 这个需要视具体场景与项目的不同而灵活变动, 比如: route, component, utils, build…

## commit subject
subject commit 的概述, 建议符合 50/72 formatting

使用第一人称现在时的动词开头, 比如 modify 而不是 modified 或 modifies.
首字母小写, 并且结尾不加句号.

## commit body
body 其实就是 subject 的详细说明, 可以分为多行, 建议符合 50/72 formatting

## commit footer
footer 一些备注, 通常是 BREAKING CHANGE 或修复的 bug 的链接.

## commit cz template

```js
  
'use strict';

module.exports = {

  types: [
    {
      value: '💪WIP',
      name : '💪  WIP:      Work in progress'
    },
    {
      value: '✨feat',
      name : '✨  feat:     A new feature'
    },
    {
      value: '🐞fix',
      name : '🐞  fix:      A bug fix'
    },
    {
      value: '🛠refactor',
      name : '🛠  refactor: A code change that neither fixes a bug nor adds a feature'
    },
    {
      value: '📚docs',
      name : '📚  docs:     Documentation only changes'
    },
    {
      value: '🏁 test',
      name : '🏁  test:     Add missing tests or correcting existing tests'
    },
    {
      value: '🗯chore',
      name : '🗯  chore:    Changes that don\'t modify src or test files. Such as updating build tasks, package manager'
    },
    {
      value: '💅style',
      name : '💅  style:    Code Style, Changes that do not affect the meaning of the code (white-space, formatting, missing semi-colons, etc)'
    },
    {
      value: '⏪revert',
      name : '⏪  revert:   Revert to a commit'
    }
  ],

  scopes: [],

  allowCustomScopes: true,
  allowBreakingChanges: ["feat", "fix"]
};
```



# For learning

## Branches
- master
- develop
- feature branches
- release branches
- hotfix branches

## commit scope 
scope commit 影响的范围, 比如数据层, 控制层, 视图层等等, 这个需要视具体场景与项目的不同而灵活变动, 比如:

- notes
- codes
- projects

## commit subject
subject commit 的概述, 建议符合 50/72 formatting

使用第一人称现在时的动词开头, 比如 modify 而不是 modified 或 modifies.
首字母小写, 并且结尾不加句号.

## commit body
body 其实就是 subject 的详细说明, 可以分为多行, 建议符合 50/72 formatting

## commit footer
footer 一些备注, 通常是 BREAKING CHANGE 或修复的 bug 的链接.

## commit cz template

```js
  
'use strict';

module.exports = {

  types: [
    {
      value: '💪WIP',
      name : '💪  WIP:      Work in progress'
    },
    {
      value: '📚docs',
      name : '📚  docs:     Documentation only changes'
    },
    {
      value: '🧑‍💻codes',
      name : '🧑‍💻  codes:    Some new codes'
    },
    {
      value: '⛑projects',
      name : '⛑  projects: A new project'
    },
    {
      value: '💼files',
      name : '💼  files:    Some configuration or backup files'
    },    
    {
      value: '✨feat',
      name : '✨  feat:     A new feature'
    },
    {
      value: '🐞fix',
      name : '🐞  fix:      A bug or mistake fix'
    },
    {
      value: '🛠refactor',
      name : '🛠  refactor: A code or structure change that neither fixes a bug nor adds a feature'
    },
    {
      value: '🏁test',
      name : '🏁  test:     Add missing tests or correcting existing tests'
    },
    {
      value: '🗯chore',
      name : '🗯  chore:    Changes that don\'t modify src or test files. Such as updating build tasks, package manager'
    },
    {
      value: '💅style',
      name : '💅  style:    Code Style, Changes that do not affect the meaning of the code (white-space, formatting, missing semi-colons, etc)'
    },
    {
      value: '⏪revert',
      name : '⏪  revert:   Revert to a commit'
    }
  ],

  scopes: [],

  allowCustomScopes: true,
  allowBreakingChanges: ["feat", "fix"]
};
```