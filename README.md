# README

一个基于Langchain4J 的Java学习项目，关于Agent开发的一点点探索

## 小智AI学术助手介绍

可以将论文上传至文件夹 `src/main/knowledge` 当中替换 `testPaper.pdf` 文件，可以形成以本文件作为知识库的AI助手

## 已支持的额外功能

- 集成百度搜索，可以调用百度获取实时信息
- 将召回策略改造成为结合了Embedding 和 BM25 的多路召回策略

## TODO
- 前端界面
- 将每次上传的文章持久化进入向量数据库，支持多篇文章进入数据库
- 接入MCP server支持更多的工具
- 为了更好从支持文献的特点，将传统的RAG改为GraphRAG

