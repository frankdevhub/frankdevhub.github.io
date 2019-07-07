---
layout: post
title: "#Issure 201907071149# How to convert pdf to java?"
date:   2019-07-07 11:50:23
categories: demo issure java
tags: demo issure java pdf word convert
author: Franklinfang
---

* content
{:toc}

## #Issure 201907071149# How to convert pdf to java?

## java pdfbox.jar pdf 文档转换成word的实例

- maven dependency

```xml
<dependency>
     <groupId>org.apache.pdfbox</groupId>
     <artifactId>pdfbox</artifactId>
     <version>2.0.16</version>
 </dependency>
```xml






## Pdf2WordUtils

```java
package com.frankdevhub.document;

import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * <p>Title:@ClassName Pdf2WordUtils.java</p>
 * <p>Description:convert pdf document to word document</p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>
 *
 * @Author: frankdevhub@gmail.com
 * @CreateDate: 2019/7/7 10:16
 * @Version: 1.0
 */
public class Pdf2WordUtils {

    private final String fileName; //default using file under resource folder
    private File sourceFile;

    public Pdf2WordUtils(String pdfFileName) {
        this.fileName = pdfFileName;
    }

    private boolean isPdfFile() throws Exception {
        //validate fileName format
        if (StringUtils.isBlank(fileName) || StringUtils.isEmpty(fileName))
            throw new Exception("[Error:] source file Name should not be blank or empty.");
        int index = fileName.lastIndexOf(".");
        if (index == -1)
            throw new Exception(String.format("[Error:]file name [%s] invalid", fileName));
        String surffix = fileName.substring(index + 1);
        if (!"pdf".equals(surffix))
            throw new Exception(String.format("[Error:]file name [%s] invalid", fileName));
        File sourceFile = new File(fileName);
        this.sourceFile = sourceFile;
        return true;
    }

    public void convertPdf2Word() throws Exception {
        if (isPdfFile()) {
            //convert pdf2word document
            PDDocument pdfDocument = PDDocument.load(new File(fileName));
            int pageCount = pdfDocument.getNumberOfPages();
            //generate word document file name
            int index = fileName.lastIndexOf(".");
            String prefix = fileName.substring(0, index);
            String convertDocName = prefix + "." + "doc";

            System.out.println(String.format("[convert file name:]%s", convertDocName));
            File convertFile = new File(convertDocName);


            //default output path under current root
            if (!convertFile.exists())
                convertFile.createNewFile();
            FileOutputStream stream = new FileOutputStream(new File(convertDocName));
            Writer writer = new OutputStreamWriter(stream, "UTF-8");
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);// page sort
            stripper.setStartPage(1);// set start page
            stripper.setEndPage(pageCount);// set end page
            stripper.writeText(pdfDocument, writer);

            writer.close();
            pdfDocument.close();

            System.out.println(String.format("file convert success:[%s] to [%s]", sourceFile, convertDocName));
        }
    }

    public static void main(String[] args) throws Exception {
        String fileName = "src/main/resources/enum.pdf";
        Pdf2WordUtils utils = new Pdf2WordUtils(fileName);
        utils.convertPdf2Word();
    }

}

```