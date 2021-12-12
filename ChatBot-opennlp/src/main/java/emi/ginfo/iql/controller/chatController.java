package emi.ginfo.iql.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import emi.ginfo.iql.ChatBotOpennlpApplication;
import emi.ginfo.iql.Repository.historiqueRepository;
import emi.ginfo.iql.Repository.reponseRepository;
import emi.ginfo.iql.model.chatbot;
import emi.ginfo.iql.model.historique;
import emi.ginfo.iql.model.reponse;
import opennlp.tools.doccat.BagOfWordsFeatureGenerator;
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.doccat.FeatureGenerator;
import opennlp.tools.lemmatizer.LemmatizerME;
import opennlp.tools.lemmatizer.LemmatizerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;
import opennlp.tools.util.model.ModelUtil;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/")
public class chatController {
//	@RequestMapping("/")
//    public String index() {
//        return "index.html";
//    }
	
	@Autowired
	private historiqueRepository hr;
//	@PostMapping("/msg")
//    public historique HISTORIQUE(@RequestBody String message) {
//		historique h=new historique(message);
//		//h.add(message);
//		System.out.println("$$$$$$$$$$$$$$$");
//		return hr.save(h);
//		System.out.println("$$$$$$$$$$$$$$$");
		
		
//	}
	
	@Autowired
	reponseRepository rr;
	    @PostMapping("/msg")
	    public chatbot postBody(@RequestBody String message) throws IOException, IOException {
//    		historique h=new historique(message);
//			
//			System.out.println("$$$$$$$$$$$$$$$");
//			 hr.save(h);
	    		Map<String, String> questionAnswer = new HashMap<>();

	    		for(reponse r:rr.findAll()) {
	    			questionAnswer.put(r.getCategorie(),r.getReponse());
	    		}
	    		
//	    		questionAnswer.put("greeting", "Salut! Bienvenue et merci de votre visite. Comment puis-je vous aider aujourd'hui?");
//	    		questionAnswer.put("product-inquiry",
//	    				"The purpose of our Banque is to find and give you all your requirement");
//	    		questionAnswer.put("price-inquiry", "there is no price for client services, it's free and we are happy to serve our clients");
//	    		questionAnswer.put("conversation-continue", "What else can I help you with ?");
//	    		questionAnswer.put("conversation-complete", "Nice chatting with you. Bye.");
//	    		
//	    		questionAnswer.put("salutation", "Bonjour et bienvenue dans la platforme Banque, avez vous des questions ?.");
//	    		questionAnswer.put("compte", "pour créer un nouveau compte client, vous avez besoin de vous diriger "
//	    				+ "vers l'agence bancaire la plus proche ?.");
//	    		questionAnswer.put("transfert", "Oui, les comptes courants peuvent être transférés d'une succursale à une autre. Cependant, il existe certaines restrictions. "
//	    				+ "Veuillez vous rendre dans votre succursale la plus proche pour plus de détails.");
//	    		questionAnswer.put("perte-de-carte", "Si vous perdez votre carte, veuillez contacter immédiatement notre centre de support client");
	    		// Train categorizer model to the training data we created.
	    				DoccatModel model = trainCategorizerModel();

	    				
	    				while (true) {

	    					//  input .
	    					
	    					String userInput = message;

	    					// decomposer input à des phrases
	    					String[] sentences = breakSentences(userInput);

	    					String answer = "";
	    					boolean conversationComplete = false;

	    					// parcourir la phrase
	    					for (String sentence : sentences) {

	    						// separer les mots de la phrase avec tokenizer.
	    						String[] tokens = tokenizeSentence(sentence);

	    						// Tag separated words with POS tags to understand their gramatical structure.
	    						String[] posTags = detectPOSTags(tokens);

	    						// Lemmatizer chaque mot pour categoriser
	    						String[] lemmas = lemmatizeTokens(tokens, posTags);
	    						//determiner la meilleur categorie de l'input avec lemmatized tokens 
	    						
	    						String category = detectCategory(model, lemmas);

	    						// Get predefined answer from given category & add to answer.
	    						answer = answer + " " + questionAnswer.get(category);

	    						// If category conversation-complete, we will end chat conversation.
	    						if ("conversation-complete".equals(category)) {
	    							conversationComplete = true;
	    						}
	    					}

	    					
	    					
	    					return new chatbot(message, answer);
	    							
//	    					if (conversationComplete) {
//	    						break;
//	    					}
	    				}
						

	    	}

	    	/**
	    	 * Train categorizer model as per the category sample training data we created.
	    	 * 
	    	 * @return
	    	 * @throws FileNotFoundException
	    	 * @throws IOException
	    	 */
	    	private static DoccatModel trainCategorizerModel() throws FileNotFoundException, IOException {
	    		// faq-categorizer.txt is a custom training data with categories as per our chat
	    		// requirements.
	    		InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(new File("faq-categorizer.txt"));
	    		ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, StandardCharsets.UTF_8);
	    		ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

	    		DoccatFactory factory = new DoccatFactory(new FeatureGenerator[] { new BagOfWordsFeatureGenerator() });

	    		TrainingParameters params = ModelUtil.createDefaultTrainingParameters();
	    		params.put(TrainingParameters.CUTOFF_PARAM, 0);

	    		// Train a model with classifications from above file.
	    		DoccatModel model = DocumentCategorizerME.train("en", sampleStream, params, factory);
	    		return model;
	    	}

	    	/**
	    	 * Detect category using given token. Use categorizer feature of Apache OpenNLP.
	    	 * 
	    	 * @param model
	    	 * @param finalTokens
	    	 * @return
	    	 * @throws IOException
	    	 */
	    	private static String detectCategory(DoccatModel model, String[] finalTokens) throws IOException {

	    		// Initialize document categorizer tool
	    		DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);

	    		// Get best possible category.
	    		double[] probabilitiesOfOutcomes = myCategorizer.categorize(finalTokens);
	    		String category = myCategorizer.getBestCategory(probabilitiesOfOutcomes);
	    		System.out.println("Category: " + category);

	    		return category;

	    	}

	    	
	    	private static String[] breakSentences(String data) throws FileNotFoundException, IOException {
	    		// Better to read file once at start of program & store model in instance
	    		// variable. but keeping here for simplicity in understanding.
	    		try (InputStream modelIn = new FileInputStream("en-sent.bin")) {

	    			SentenceDetectorME myCategorizer = new SentenceDetectorME(new SentenceModel(modelIn));

	    			String[] sentences = myCategorizer.sentDetect(data);
	    			System.out.println("Sentence Detection: " + Arrays.stream(sentences).collect(Collectors.joining(" | ")));

	    			return sentences;
	    		}
	    	}

	    	
	    	private static String[] tokenizeSentence(String sentence) throws FileNotFoundException, IOException {
	    		// Better to read file once at start of program & store model in instance
	    		// variable. but keeping here for simplicity in understanding.
	    		try (InputStream modelIn = new FileInputStream("en-token.bin")) {

	    			// Initialize tokenizer tool
	    			TokenizerME myCategorizer = new TokenizerME(new TokenizerModel(modelIn));

	    			// Tokenize sentence.
	    			String[] tokens = myCategorizer.tokenize(sentence);
	    			System.out.println("Tokenizer : " + Arrays.stream(tokens).collect(Collectors.joining(" | ")));

	    			return tokens;

	    		}
	    	}

	    	/**
	    	 * Find part-of-speech or POS tags of all tokens using POS tagger feature of
	    	 * Apache OpenNLP.
	    	 * 
	    	 * @param tokens
	    	 * @return
	    	 * @throws IOException
	    	 */
	    	private static String[] detectPOSTags(String[] tokens) throws IOException {
	    		// Better to read file once at start of program & store model in instance
	    		// variable. but keeping here for simplicity in understanding.
	    		try (InputStream modelIn = new FileInputStream("en-pos-maxent.bin")) {

	    			// Initialize POS tagger tool
	    			POSTaggerME myCategorizer = new POSTaggerME(new POSModel(modelIn));

	    			// Tag sentence.
	    			String[] posTokens = myCategorizer.tag(tokens);
	    			System.out.println("POS Tags : " + Arrays.stream(posTokens).collect(Collectors.joining(" | ")));

	    			return posTokens;

	    		}

	    	}

	    	/**
	    	 * Find lemma of tokens using lemmatizer feature of Apache OpenNLP.
	    	 * 
	    	 * @param tokens
	    	 * @param posTags
	    	 * @return
	    	 * @throws InvalidFormatException
	    	 * @throws IOException
	    	 */
	    	private static String[] lemmatizeTokens(String[] tokens, String[] posTags)
	    			throws InvalidFormatException, IOException {
	    		// Better to read file once at start of program & store model in instance
	    		// variable. but keeping here for simplicity in understanding.
	    		try (InputStream modelIn = new FileInputStream("en-lemmatizer.bin")) {

	    			// Tag sentence.
	    			LemmatizerME myCategorizer = new LemmatizerME(new LemmatizerModel(modelIn));
	    			String[] lemmaTokens = myCategorizer.lemmatize(tokens, posTags);
	    			System.out.println("Lemmatizer : " + Arrays.stream(lemmaTokens).collect(Collectors.joining(" | ")));

	    			return lemmaTokens;

	    		}
	    	}

	    }
	
//	@RequestMapping(value="/test", method=RequestMethod.POST)
//    public @ResponseBody String test( chatbot ch){
//        return "HELLO TEST : " + testObj;
//    }
//	@RequestMapping(value="/formR",method=RequestMethod.GET)
//	 public String FormReclamation(Model model) {
//		 model.addAttribute("reclamation",new Reclamation());
//	 return "reclamation";
//	 }
//		
//	@RequestMapping(value="/saveR",method=RequestMethod.POST)
//	public String save(@Valid Reclamation reclamation,BindingResult bindingResult) {
//		  if(bindingResult.hasErrors()) {
//			  return "reclamation"; }
//		  reclamationRepository.save(reclamation);
//		  return "reclamation"; }	 
//	

