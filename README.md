# RAG (Retrieval-Augmented Generation) – Spring AI Learning Project

This project demonstrates how to implement **Retrieval-Augmented Generation (RAG)** using a **text file** as the knowledge source with **Spring AI**.

The goal of this project is to **learn RAG step-by-step**, starting from manual approaches and gradually moving toward **cleaner, production-style APIs**.

---

## Common Configuration

### `Config/AiConfig`
- Produces a **`VectorStore` bean** (in-memory vector store).
- This store is used to:
  - chunk documents
  - generate embeddings
  - store and retrieve relevant context for RAG queries

---

## Controllers Overview

The project contains **three controllers**, each demonstrating a different way of interacting with RAG and LLMs.

---

## Controller 1: `RAGController`

This controller exposes **three APIs** to demonstrate incremental RAG concepts.

### **V1**
- Loads documents from a text file.
- Splits (chunks) them into smaller pieces.
- Stores the chunks in the **`VectorStore`** (Autowired bean).
- Executes a query and returns results based on retrieved documents.

### **V2**
- Performs the same operations as **V1**.
- Additionally uses **similarity search** to improve retrieval quality.
- The similarity logic can be found in the `AiService` class.

### **V3**
- A **simple `ChatModel` API**.
- Does **not** use `sampleDocuments` or restrict answers to the text file.
- This demonstrates a **pure LLM chat** without RAG.
- RAG-based contextualisation is shown in the next controller.

---

## Controller 2: `UserAndSystemChatRagController`

This controller demonstrates **manual RAG using system and user messages**.

### Exposed API
- `/query`

### Flow
- Performs a document query similar to **V1** in `RAGController`.
- Instead of a `String`, it works with a **`List<Document>`**.
- Uses `ChatModel` and constructs a `Prompt` with two inputs:

#### 1️⃣ `UserMessage`
- Contains the user query  
  *(e.g. “What did the author do while growing up?”)*

#### 2️⃣ `SystemMessage`
- Provides instructions and context to the LLM.
- Created in `AiService` via the `getSystemMessage()` method.  
- Instructions are loaded from: resources/prompt/system.ts
### Result
- The `ChatModel` uses:
- the **user query**
- the **system instructions**
- the **retrieved documents**
- to generate a context-aware response.

---

## Controller 3: `ChatClientController`

This controller demonstrates the **same RAG behavior as Controller 2**, but using the **`ChatClient` API**.

### Exposed API
- `/query`

### Key Differences
- Uses **`ChatClient` instead of directly calling `ChatModel`**.
- Significantly **simpler and cleaner code**.
- RAG logic and behavior remain the same.
- Represents a **more production-ready approach**.

---

## Learning Progression Summary

- **Controller 1** → Basic RAG concepts and similarity search  
- **Controller 2** → Manual RAG with `ChatModel`, `UserMessage`, and `SystemMessage`  
- **Controller 3** → Cleaner, production-style RAG using `ChatClient`

---

## Key Takeaway

This project intentionally shows **multiple ways to implement RAG**, helping understand:
- what happens under the hood
- when to use manual control
- when to rely on higher-level abstractions like `ChatClient`  
