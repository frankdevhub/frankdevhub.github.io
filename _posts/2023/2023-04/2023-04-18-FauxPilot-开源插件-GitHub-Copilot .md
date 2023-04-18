---
layout: post
title:  "FauxPilot：可本地运行的开源 GitHub Copilot （Copilot Plugin）"
categories: GitHub Copilot OpenAI 开源插件
tags:  Copilot OpenAI 开源插件
author: Franklinfang
---

* content
{:toc}


GitHub Copilot 是 GitHub 去年 6 月推出的人工智能模型，这是一个利用机器学习技术为开发者提供代码建议和代码补全的工具，能够帮助开发者更快完成编程任务。但由于 GitHub Copilot 训练使用的数据集，以及该工具如今成为了一款向开发者收费的商业性产品，GitHub Copilot 也引发了一些争议。

那有没有一个能够替代 GitHub Copilot 的工具呢？


近日美国纽约大学计算机科学和工程系助理教授 Brendan Dolan-Gavitt 开源了一个名为 FauxPilot 的项目，根据介绍，这是 GitHub Copilot 的替代品，能够在本地运行并且不会上传用户的数据，如果开发者使用的是自己训练的 AI 模型，也无需再担心生成代码的许可问题。


GitHub Copilot 依赖于 OpenAI Codex，后者是一个基于 GPT-3 的自然语言转代码系统，使用了存储在 GitHub 上的 "数十亿行公共代码" 进行训练。而 FauxPilot 并没有使用 Codex，为了方便开发者使用它依赖了 Salesforce 的 CodeGen 模型，CodeGen 同样也是使用公共开源代码进行训练的。


目前 Salesforce CodeGen 提供了 3.5 亿、20 亿、60 亿 和 160 亿参数的模型，但在 FauxPilot 这边只看到 3.5 亿、60 亿和 160 亿的模型，暂时没有 20 亿模型可用，这就对训练模型需要使用的 GPU 提出了较高的要求。因为 3.5 亿参数的模型仅需要 2GB VRAM；而稍高一个档次的 60 亿参数模型所需要的 VRAM 就大幅上涨到了 13GB，这就需要至少 RTX 3090 的显卡才能跑，就更不用说 160 亿的模型了。



![image](https://raw.githubusercontent.com/frankdevhub/frankdevhub.github.io/master/_posts/2023/2023-04/22cbc40a18df03a0d3492702cae9c4f2d.png)

