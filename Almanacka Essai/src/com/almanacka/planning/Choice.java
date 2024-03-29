package com.almanacka.planning;

public class Choice
{
    private int _weight;
    private String _choiceId;
    
    public Choice (int weight, String choice)
    {
    	_weight = weight;
    	_choiceId = choice;
    }
    public int GetWeight()
    {
    	return _weight;
    }

	@SuppressWarnings("unused")
	private void SetWeight( int weightToSet )
    {
    	_weight = weightToSet;
    }
    
    public String GetChoiceId()
    {
    	return _choiceId;
    }
    
	@SuppressWarnings("unused")
	private void SetChoiceId( String choiceIdToSet )
    {
    	_choiceId = choiceIdToSet;
    }
}
