package bayesGame.fluff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomSubjectVariable {
	
	private ArrayList<String> list;
	private int pointer = 0;
	
	public static final String[] ALPHABET_SET_VALUES = new String[]{
		"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"	
	};
	
	public static final String[] PSYCHOLOGY_SET_VALUES = new String[] { "Attachment", 
		"ageism",
		"the anchoring heuristic",
		"anxiety",
		"the auditory cortex",
		"the availability heuristic",
		"behaviorism",
		"belief-bias",
		"bipolar disorder",
		"body image",
		"the bystander effect",
		"the cerebellum",
		"the cerbral cortex",
		"chunking",
		"circadian rhythms",
		"cognitive maps",
		"concepts",
		"conformity",
		"the consistency paradox",
		"coping",
		"creativity",
		"declarative memory",
		"delusions",
		"diffusion of responsibility",
		"double-blinding",
		"dopamine",
		"the ego",
		"emotions",
		"the endocrine system",
		"episodic memory",
		"estrogen",
		"expectancy",
		"fear",
		"the five-factor model",
		"fluid intelligence",
		"framing",
		"the frontal lobe",
		"functionalism",
		"gender",
		"genotype",
		"gestalt psychology",
		"ground",
		"group dynamics",
		"hallucinations",
		"heredity",
		"heuristics",
		"the hippocampus",
		"hormones",
		"hypnosis",
		"imprinting",
		"inference",
		"in-group bias",
		"insomnia",
		"intimacy",
		"language production",
		"learning",
		"the limbic system",
		"manic episodes",
		"meditation",
		"mental age",
		"motivation",
		"the motor cortex",
		"natural selection",
		"neurons",
		"the normal curve",
		"object permanence",
		"observer bias",
		"the olfactory bulb",
		"out-groups",
		"overregularization",
		"pain",
		"parental investment",
		"personality",
		"phobias",
		"the placebo effect",
		"prototypes",
		"puberty"};

	public RandomSubjectVariable() {
		list = new ArrayList<String>(Arrays.asList(ALPHABET_SET_VALUES));
	}
	
	public RandomSubjectVariable(String[] variables){
		list = new ArrayList<String>(Arrays.asList(variables));
	}
	
	public void shuffle(){
		if (pointer == 0){
			Collections.shuffle(list);
		}
	}
	
	public String getNewRandomTerm(){
		String newTerm = list.get(pointer);
		pointer++;
		return newTerm;
	}
	
	public String getOldRandomTerm(){
		if (pointer == 0){
			return getNewRandomTerm();
		}
		
		Random rn = new Random();
		int oldTermLocation = rn.nextInt(pointer);
		String oldTerm = list.get(oldTermLocation);
		return oldTerm;
	}

}
