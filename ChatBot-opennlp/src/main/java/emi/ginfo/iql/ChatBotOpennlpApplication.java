package emi.ginfo.iql;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import emi.ginfo.iql.Repository.historiqueRepository;
import emi.ginfo.iql.Repository.reponseRepository;
import emi.ginfo.iql.model.historique;
import emi.ginfo.iql.model.reponse;

@SpringBootApplication
public class ChatBotOpennlpApplication {
//utiliser pile elk
	@Autowired
	private reponseRepository hr;

	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
		SpringApplication.run(ChatBotOpennlpApplication.class, args);
		

	}
	@Bean
    public CommandLineRunner console() {
        return args -> {
        	System.out.println("$$$$");
//        	reponse r1=new reponse("greeting","Salut! Bienvenue et merci de votre visite. Comment puis-je vous aider aujourd'hui");
//        	hr.save(r1);
//        	reponse r1=new reponse("compte", "pour créer un nouveau compte client, vous avez besoin de vous diriger ");
//        	hr.save(r1);
//        	reponse r1=new reponse("eljadida", "L'agence centrale d'El Jadida, se trouve à l'adresse suivante:"
//        			+ "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3336.5497860466035!2d-8.505739049520217!3d33.2520962807371!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xda91de9a3f92293%3A0xe06512d820396ca1!2sBanque%20Populaire!5e0!3m2!1sfr!2sma!4v1637697554122!5m2!1sfr!2sma\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\"></iframe>");
//        	
//        	hr.save(r1);
//        	reponse r1=new reponse("perte-de-carte", "Si vous perdez votre carte, veuillez contacter immédiatement notre centre de support client ou vous dirigez vers l'agence bancaire la plus proche.");
//        	
//        	hr.save(r1);
//reponse r2=new reponse("transfert", "Si vous perdez votre carte,Oui, les comptes courants peuvent être transférés d'une succursale à une autre. Cependant, il existe certaines restrictions.");
//        	hr.save(r2);
//        	reponse r3=new reponse("rabat", "L'agence de Rabat se trouve à la localisation suivante :  https://goo.gl/maps/eLR7UXjXmSY6qSTW7 ");
//        	hr.save(r3);
//        	
        	System.out.println("$$$$$$$saving in data$$$$$");

        };}
//	public void run(String... args) throws Exception {
//		// TODO Auto-generated method stub
////		historique h1=new historique(1,"hi");
////		
////		hr.save(h1);
//		
//		System.out.println("$$$$$$$saving in data$$$$$");
//		
//		
//	}
	// commandLineRunner
    
}

