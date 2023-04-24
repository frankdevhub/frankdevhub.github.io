---
layout: post
title:  "ChatGPT 和 Elasticsearch的结合：在私域数据上使用ChatGPT"
categories: ChatGPT Elasticsearch OpenAI
tags:  ChatGPT Elasticsearch OpenAI
author: Franklinfang
---

* content
{:toc}


![image](https://raw.githubusercontent.com/frankdevhub/frankdevhub.github.io/master/_posts/2022/2023-04/e38d714a88fcf90cb1607340ec41bd58.png)

如何结合 Elasticsearch 的搜索相关性和 OpenAI 的 ChatGPT 的问答功能来查询您的数据？在此博客中，您将了解如何使用 Elasticsearch 将 ChatGPT 连接到专有数据存储，并为您的数据构建问答功能。

![image](https://raw.githubusercontent.com/frankdevhub/frankdevhub.github.io/master/_posts/2022/2023-04/6d492cab1ab2337ffb6f6c249e67085e.png)

## 什么是ChatGPT？

近几个月来，人们对 ChatGPT 充满了热情，这是一种由 OpenAI 创建的开创性人工智能模型。但 ChatGPT 到底是什么？ 

基于强大的 GPT 架构，ChatGPT 旨在理解文本输入并生成类似人类的响应。GPT 代表“生成式预训练Transformer（Generative Pre-trained Transformer）”。Transformer 是一种尖端模型架构，彻底改变了自然语言处理 (NLP) 领域。这些模型在海量数据上进行了预训练，能够理解上下文、生成相关响应，甚至进行对话. 要了解更多关于 transformer 模型的历史和 Elastic Stack 中的一些 NLP 基础知识，请务必查看很棒的[Elastic ML 工程师 Josh Devins 的演讲](https://www.youtube.com/watch?v=SvvbMCwyOnU)。

ChatGPT 的主要目标是促进人机之间有意义且引人入胜的交互。通过利用 NLP 的最新进展，ChatGPT 模型可以提供广泛的应用程序，从聊天机器人和虚拟助手到内容生成、代码完成等等。这些人工智能驱动的工具已迅速成为无数行业的宝贵资源，帮助企业简化流程并增强服务。

然而，尽管 ChatGPT 具有不可思议的潜力，但用户仍应注意某些限制。一个值得注意的限制是知识截止日期。目前，ChatGPT 接受的数据训练截至 2021 年 9 月，这意味着它不知道此后发生的事件、发展或变化。因此，用户在依赖 ChatGPT 获取最新信息时应牢记这一限制。在讨论快速变化的知识领域（例如软件增强和功能甚至世界大事）时，这可能会导致反应过时或不正确。 

ChatGPT 虽然是一种令人印象深刻的 AI 语言模型，但偶尔会在其响应中产生幻觉，当它无法访问相关信息时通常会加剧这种情况。这种过度自信会导致向用户提供不正确的答案或误导性信息。重要的是要意识到这一限制，并在必要时以一定程度的怀疑态度、交叉检查和验证信息来处理 ChatGPT 生成的响应，以确保准确性和可靠性。

ChatGPT 的另一个限制是它缺乏关于特定领域内容的知识。虽然它可以根据接受过培训的信息生成连贯且与上下文相关的响应，但它无法访问特定领域的数据或提供依赖于用户独特知识库的个性化答案。例如，它可能无法深入了解组织的专有软件或内部文档。因此，用户在直接从 ChatGPT 寻求有关此类主题的建议或答案时应谨慎行事。

最小化这些限制的一种方法是为 ChatGPT 提供对与您的域和问题相关的特定文档的访问权限，并启用 ChatGPT 的语言理解功能以生成定制的响应。

这可以通过将 ChatGPT 连接到 Elasticsearch 等搜索引擎来实现。






## Elasticsearch——you know, for search!

Elasticsearch 是一个高效的搜索引擎，旨在提供相关文档检索，确保用户可以快速准确地访问他们需要的信息。Elasticsearch 的主要重点是向用户提供最相关的结果、简化搜索过程并增强用户体验。

Elasticsearch 拥有众多可确保一流搜索性能的功能，包括支持传统关键字和基于文本的搜索 ( [BM25](https://www.elastic.co/guide/en/elasticsearch/reference/current/index-modules-similarity.html) )以及一个具备精确匹配和近似kNN的AI向量搜索（[k-Nearest Neighbor](https://www.elastic.co/guide/en/elasticsearch/reference/current/knn-search.html)）。这些高级功能使 Elasticsearch 不仅可以检索相关的结果，还可以检索使用自然语言表达的查询的结果。通过利用传统、向量或混合搜索 (BM25 + kNN)，Elasticsearch 可以提供无与伦比的精确结果，帮助用户轻松找到他们需要的信息。

Elasticsearch 的主要优势之一是其强大的 API，它可以与其他服务无缝集成以扩展和增强其功能。通过将 Elasticsearch 与各种第三方工具和平台集成，用户可以根据自己的特定需求创建功能强大的自定义搜索解决方案。这种灵活性和可扩展性使 Elasticsearch 成为希望提高搜索能力并在竞争激烈的数字环境中保持领先地位的企业的理想选择。

通过与 ChatGPT 等高级人工智能模型协同工作，Elasticsearch 可以为 ChatGPT 提供最相关的文档以用于其响应。Elasticsearch 和 ChatGPT 之间的这种协同作用可确保用户收到与其查询相关的事实、上下文相关和最新的答案。从本质上讲，Elasticsearch 的检索能力与 ChatGPT 的自然语言理解能力相结合，提供了无与伦比的用户体验，为信息检索和 AI 支持的协助树立了新标准。

## 如何将 ChatGPT 与 Elasticsearch 结合使用

![image](https://raw.githubusercontent.com/frankdevhub/frankdevhub.github.io/master/_posts/2022/2023-04/b402aba80bc45e5876c83f2e0f35a019.png)

1. Python API接受用户提问。
2. 
	为 Elasticsearch 生成混合搜索请求
	- title字段上的 BM25 匹配
	- kNN 搜索title向量字段
	- 提升 kNN 搜索结果以对齐分数
	- 设置 size=1 只返回得分最高的文档

2. 搜索请求发送到Elasticsearch。

3. 文档正文和原始url返回给python应用程序。

	1. 对 OpenAI ChatCompletion 进行 API 调用：
	2. prompt："answer this question <question> using only this document <body_content from top search result>"
	3. 生成的响应返回给 python。
	4. Python 将原始文档源 url 添加到生成的响应中，并将其打印到屏幕上供用户使用。
 
ElasticDoc ChatGPT 流程利用 Python 界面接受用户问题并为 Elasticsearch 生成混合搜索请求，结合 BM25 和 kNN 搜索方法从 Elastic的官方文档中查找最相关的文档，这些文档现已在 Elasticsearch 中编制索引。但是，**您不必使用混合搜索甚至向量搜索。Elasticsearch 可以灵活地使用最适合您需求的搜索模式，并为您的特定数据集提供最相关的结果。**

在检索到最佳结果后，该程序会为 OpenAI 的 ChatCompletion API 制作Prompt，指示它仅使用所选文档中的信息来回答用户的问题。此提示是确保 ChatGPT 模型仅使用官方文档中的信息、这是减少ChatGPT产生幻觉的关键。

最后，该程序向用户展示 API 生成的响应和源文档的链接，提供无缝且用户友好的体验，集成了前端交互、Elasticsearch 查询和 OpenAI API 使用以实现高效的问答。

请注意，虽然为简单起见我们只返回得分最高的文档，但最佳做法是返回多个文档以为 ChatGPT 提供更多上下文。可以在不止一个文档页面中找到正确的答案，或者如果我们要为完整的正文文本生成向量，那么这些较大的文本正文可能需要分块并存储在多个 Elasticsearch 文档中。通过利用 Elasticsearch 与传统搜索方法协同搜索大量矢量字段的能力，您可以显着提高您的顶级文档召回率。

## 技术设置

技术要求相当低，但需要一些步骤才能将所有部分组合在一起。对于此示例，我们将配置[Elasticsearch 网络爬虫](https://www.elastic.co/cn/web-crawler)以摄取 Elastic 文档并在摄取时为title生成向量。您可以跟随本文并复制此设置，或使用自己的数据。为了跟随本文，我们需要：

- Elasticsearch集群
- Eland Python 库
- OpenAI API 账号

运行我们的 python 前端和 api 后端的服务器

## Elastic Cloud设置

本节中的步骤假设您当前没有在 Elastic Cloud 中运行的 Elasticsearch 集群。如果你已经有一个 Elastic Cloud 

的集群，可以跳到下一部分。

### 注册

如果您还没有 Elasticsearch 集群，您可以通过[Elastic Cloud](https://cloud.elastic.co/registration)注册免费试用。

![image](https://raw.githubusercontent.com/frankdevhub/frankdevhub.github.io/master/_posts/2022/2023-04/1ddae6f52507fbc2b0305f8272f6cc63.png)