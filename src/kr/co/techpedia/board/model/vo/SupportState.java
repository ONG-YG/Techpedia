package kr.co.techpedia.board.model.vo;

public class SupportState {
	
	private String spptStatCD;
	private String statName;
	private String statSuperviser;
	
	
	public SupportState() {
		super();
	}
	public SupportState(String spptStatCD, String statName, String statSuperviser) {
		super();
		this.spptStatCD = spptStatCD;
		this.statName = statName;
		this.statSuperviser = statSuperviser;
	}
	
	
	public String getSpptStatCD() {
		return spptStatCD;
	}
	public void setSpptStatCD(String spptStatCD) {
		this.spptStatCD = spptStatCD;
	}
	public String getStatName() {
		return statName;
	}
	public void setStatName(String statName) {
		this.statName = statName;
	}
	public String getStatSuperviser() {
		return statSuperviser;
	}
	public void setStatSuperviser(String statSuperviser) {
		this.statSuperviser = statSuperviser;
	}
	
	@Override
	public String toString() {
		String supportState = "-----------------------------------------------\n"
							+"spptStatCD : "+spptStatCD+"\n"
							+"statName : "+statName+"\n"
							+"statSuperviser : "+statSuperviser+"\n"
							+"-----------------------------------------------\n";
		
		return supportState;
	}
}
