# CHECK COMMON LIBRARY

This is a small library project for tracing method execution inside a Java project.

## ✨ Features

Check Common Library provides the following features:

### 🔍 1. Audit Logging
The library audits the execution of public methods inside your controllers, logging:
- Method name
- Return values
- Execution status (success/failure)

### ⏱ 2. Execution Time Measurement
It measures the execution time of  methods and logs how long they took to run.

### 🔁 3. Retry on Failure
It provides a custom annotation `@RetryOnFailure` that automatically retries a method upon failure based on:
- Maximum attempts
- Delay between retries
- Specific included/excluded exception types

## 📦 Installation

This library is published on Maven Central. Add the following dependency to your project:

```xml
<dependency>
    <groupId>com.tonorganisation</groupId>
    <artifactId>checkcommonlib</artifactId>
    <version>1.0.0</version>
</dependency>
