package peter.tool;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {"modeValue", "sourceFolderPath", "outputFolderPath"})
public class ContinuousPictureHTMLFileGeneratorSetting {
	private String modeValue;
	private String sourceFolderPath;
	private String outputFolderPath;
	
	public ContinuousPictureHTMLFileGeneratorSetting(){
		
	}
	
	public ContinuousPictureHTMLFileGeneratorSetting(String modeValue, String sourceFolderPath, String outputFolderPath) {
		this.modeValue = modeValue;
		this.sourceFolderPath = sourceFolderPath;
		this.outputFolderPath = outputFolderPath;
	}
	
	@XmlElement
	public String getModeValue() {
		return modeValue;
	}
	public void setModeValue(String modeValue) {
		this.modeValue = modeValue;
	}
	@XmlElement
	public String getSourceFolderPath() {
		return sourceFolderPath;
	}
	public void setSourceFolderPath(String sourceFolderPath) {
		this.sourceFolderPath = sourceFolderPath;
	}
	@XmlElement
	public String getOutputFolderPath() {
		return outputFolderPath;
	}
	public void setOutputFolderPath(String outputFolderPath) {
		this.outputFolderPath = outputFolderPath;
	}	
}
