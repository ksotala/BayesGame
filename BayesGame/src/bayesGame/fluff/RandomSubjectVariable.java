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
	
	private final String[] ALPHABET_SET_VALUES = new String[]{
		"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"	
	};
	
	private final String[] PSYCHOLOGY_SET_VALUES = new String[] { "Attachment", 
		"Ageism",
		"Anchoring heuristic",
		"Anxiety",
		"Auditory cortex",
		"Availability heuristic",
		"Behaviorism",
		"Belief-bias",
		"Bipolar disorder",
		"Body image",
		"Bystander effect",
		"Cerebellum",
		"Cerbral cortex",
		"Chunking",
		"Circadian rhythm",
		"Cognitive map",
		"Concepts",
		"Conformity",
		"Consistency paradox",
		"Coping",
		"Creativity",
		"Declarative memory",
		"Delusions",
		"Diffusion of responsibility",
		"Double-blinding",
		"Dopamine",
		"Ego",
		"Emotion",
		"Endocrine system",
		"Episodic memory",
		"Estrogen",
		"Expectancy",
		"Fear",
		"Five-factor model",
		"Fluid intelligence",
		"Frame",
		"Frontal lobe",
		"Functionalism",
		"Gender",
		"Genotype",
		"Gestalt psychology",
		"Ground",
		"Group dynamics",
		"Hallucinations",
		"Heredity",
		"Heuristics",
		"Hippocampus",
		"Hormones",
		"Hypnosis",
		"Imprinting",
		"Inference",
		"In-group bias",
		"Insomnia",
		"Intimacy",
		"Language production",
		"Learning",
		"Limbic system",
		"Manic episode",
		"Meditation",
		"Mental age",
		"Motivation",
		"Motor cortex",
		"Natural selection",
		"Neuron",
		"Normal curve",
		"Object permanence",
		"Observer bias",
		"Olfactory bulb",
		"Out-groups",
		"Overregularization",
		"Pain",
		"Parental investment",
		"Personality",
		"Phobia",
		"Placebo effect",
		"Prototype",
		"Puberty"};

	public RandomSubjectVariable() {
		list = new ArrayList<String>(Arrays.asList(ALPHABET_SET_VALUES));
		Collections.shuffle(list);
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
