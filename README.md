In this project We have learnt how to use RAG Retrieval-Augmented Generation using textFile  
Here are common config used in the poject  

1 -  Config/AiConfig file produces a bean of type VectorStore (in memory vector Store) for storing data.  

We have 2 controllers in the project  
## Controller1::RAGController  
  Has 3 api calls exposed  
    &emsp;V1  
      &emsp;&emsp;Api loads the documents and chunks it into small pieces so that it can store it and load it into VectorStore(Autowired Bean)  
      &emsp;&emsp;It returns the result on the basis of query passed as parameters.  
    &emsp;V2  
      &emsp;&emsp;Api does everything like V1 but uses similarity search to impact the search criteria on VectorStore (can be seen in AiService file).  
    &emsp;V3  
      &emsp;&emsp;V3 Api is simple chatModel api (which is not using (sampleDocuments) for searching the result (we are not restricting our chatModel to get the results out from the textFile only. We will do it in other controller defined in the document.  
## Controller2:: UserAndSystemChatRagController  
  &emsp;&emsp;Has only 1 api exposed  
    &emsp;&emsp;/query  
      &emsp;&emsp;&emsp;Does the query operation on Documents like V1 (RagController) but instead of String get's List<Documents>.  
      &emsp;&emsp;&emsp;Now it will use chatModel and will pass Promot using 2 parameters  
        &emsp;&emsp;&emsp;&emsp;1 - UserMessage (query as user message like 'what did author do while growing up'  
        &emsp;&emsp;&emsp;&emsp;2 - Message of type SystemMessage  
          &emsp;&emsp;&emsp;&emsp;&emsp;SystemMessage is defined in AiService in method getSystemMessage which contextualizes the chatModel with intstructions provided in     &emsp;&emsp;&emsp;&emsp;&emsp;/resources/prompt/system.ts file  
        &emsp;&emsp;&emsp;Now chatModel will use the query and systemMessage, over the List<Documents> to give results
    
    
    
