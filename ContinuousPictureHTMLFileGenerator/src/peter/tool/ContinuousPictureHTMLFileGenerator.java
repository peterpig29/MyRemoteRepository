package peter.tool;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class ContinuousPictureHTMLFileGenerator extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private static final String MODE_VALUE_OF_SINGLE_FOLDER_MODE = "S";
	private static final String MODE_VALUE_OF_PARENT_FOLDER_MODE = "P";
	private static final String MODE_VALUE_OF_GRANDPARENT_FOLDER_MODE = "G";

	private JPanel topPanel;
	
	private JRadioButton singleFolderModeRadio;
	private JRadioButton parentFolderModeRadio;
	private JRadioButton grandparentFolderModeRadio;
	
	private JPanel centerPanel;
	
	private JLabel sourceFolderPathLabel;
	private JTextField sourceFolderPathTextField;
	private JButton chooseSourceFolderButton;	
	private JFileChooser sourceFolderChooser;
	private JLabel outputFolderPathLabel;
	private JTextField outputFolderPathTextField;
	private JButton chooseOutputFolderButton;	
	private JFileChooser outputFolderChooser;	
	
	private JPanel bottomPanel;
	
	private JButton generatorButton;
	private JLabel messageLabel;
	
	private ContinuousPictureHTMLFileGeneratorService comicHTMLFileGeneratorService = new ContinuousPictureHTMLFileGeneratorService();
	private GenerateThread gt;
	
	private JAXBContext contextOfJAXB;
	
	public ContinuousPictureHTMLFileGenerator(){
		initComponents();
		initEventListener();
	}
	
	public void initComponents(){
		setTitle("漫畫網頁產生器");
		setSize(700, 175);
		setLocation(100, 100);
		
		setLayout(new BorderLayout());
		
		singleFolderModeRadio = new JRadioButton("單一資料夾模式");
		singleFolderModeRadio.setSelected(true);
		parentFolderModeRadio = new JRadioButton("母資料夾模式");
		grandparentFolderModeRadio = new JRadioButton("祖資料夾模式");
		ButtonGroup radioButtonGroup = new ButtonGroup();
		radioButtonGroup.add(singleFolderModeRadio);
		radioButtonGroup.add(parentFolderModeRadio);
		radioButtonGroup.add(grandparentFolderModeRadio);
		
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 2));
		
		topPanel.add(singleFolderModeRadio);
		topPanel.add(parentFolderModeRadio);		
		topPanel.add(grandparentFolderModeRadio);
		
		sourceFolderPathLabel = new JLabel("來源資料夾:");
		sourceFolderPathTextField = new JTextField("C:\\");
		chooseSourceFolderButton = new JButton("瀏覽");
		initSourceFolderChooser();
		outputFolderPathLabel = new JLabel("輸出資料夾:");
		outputFolderPathTextField = new JTextField("C:\\");
		chooseOutputFolderButton = new JButton("瀏覽");
		initOutputFolderChooser();
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 3));
		
		centerPanel.add(sourceFolderPathLabel);
		centerPanel.add(sourceFolderPathTextField);
		centerPanel.add(chooseSourceFolderButton);
		centerPanel.add(outputFolderPathLabel);
		centerPanel.add(outputFolderPathTextField);
		centerPanel.add(chooseOutputFolderButton);
		
		generatorButton = new JButton("產生");		
		messageLabel = new JLabel();
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(2, 1));
		
		bottomPanel.add(generatorButton);
		bottomPanel.add(messageLabel);
		
		getContentPane().add(topPanel, BorderLayout.NORTH);
		getContentPane().add(centerPanel, BorderLayout.CENTER);		
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);
	}
	
	private void initSourceFolderChooser(){
		sourceFolderChooser = new JFileChooser();
		sourceFolderChooser.setCurrentDirectory(new File("C:\\"));
		sourceFolderChooser.setDialogTitle("選擇目錄");
		sourceFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}
	
	private void initOutputFolderChooser(){
		outputFolderChooser = new JFileChooser();
		outputFolderChooser.setCurrentDirectory(new File("C:\\"));
		outputFolderChooser.setDialogTitle("選擇目錄");
		outputFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}
	
	public class GenerateThread extends Thread{

		@Override
		public void run() {
			try {
				if(singleFolderModeRadio.isSelected()){
					System.out.println(new Date() + " " + "單一資料夾模式");
					generateBySingleFolderMode("", null);
					System.out.println();
				}else if(parentFolderModeRadio.isSelected()){
					System.out.println(new Date() + " " + "母資料夾模式");
					generateByParentFolderMode();
					System.out.println();
				}else{
					System.out.println(new Date() + " " + "祖資料夾模式");
					generateByGrandparentFolderMode();
					System.out.println();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} finally{
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						generatorButton.setEnabled(true);
					    messageLabel.setText("產生完成");
					}
				});
			}
		}		
	}
	
	public void initEventListener(){
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				writeSettingToXml();				
				dispose();
			}
			
		});
		
		chooseSourceFolderButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				int option = sourceFolderChooser.showDialog(null, null);
				
				if(option == JFileChooser.APPROVE_OPTION){
					File selectedFile = sourceFolderChooser.getSelectedFile();
					sourceFolderPathTextField.setText(selectedFile.toString());
					sourceFolderChooser.setCurrentDirectory(selectedFile);
				}
			}
		});
		
		chooseOutputFolderButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				int option = outputFolderChooser.showDialog(null, null);
				
				if(option == JFileChooser.APPROVE_OPTION){
					File selectedFile = outputFolderChooser.getSelectedFile();
					outputFolderPathTextField.setText(selectedFile.toString());
					outputFolderChooser.setCurrentDirectory(selectedFile);
				}
			}
		});
		
		generatorButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						messageLabel.setText("產生中");
						generatorButton.setEnabled(false);
					}
				});
				
				gt = new GenerateThread();
				gt.start();				
			}
		});
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {	
			
			@Override
			public void run() {
				ContinuousPictureHTMLFileGenerator comicHTMLFileGenerator = new ContinuousPictureHTMLFileGenerator();
				comicHTMLFileGenerator.readSetting();				
				comicHTMLFileGenerator.setVisible(true);				
			}
			
		});
	}
	
	public void generateBySingleFolderMode(String inputFolderPath, String outputFolderName) throws UnsupportedEncodingException{
		String sourceFolderPath = null;		
		if(inputFolderPath.equals("")){
			sourceFolderPath = sourceFolderPathTextField.getText();
		}else{
			sourceFolderPath = inputFolderPath;
		}
		
		if(sourceFolderPath == null || sourceFolderPath.trim().length() == 0){
			System.out.println("請輸入漫畫資料夾路徑");
		}else if(!sourceFolderPath.contains(":\\")){
			System.out.println("請輸入正確的漫畫資料夾路徑");
		}
		
		List<String> fileNameList = comicHTMLFileGeneratorService.getFileNameList(sourceFolderPath);
		comicHTMLFileGeneratorService.convertFileNameToUrlEcoding(fileNameList);
		comicHTMLFileGeneratorService.removeMergedPictureName(fileNameList);
		
		String fileName = comicHTMLFileGeneratorService.getFolderName(sourceFolderPath);
		
		int fileAmount = comicHTMLFileGeneratorService.getFileAmount(fileNameList);
		
		String processedComicFolderPath = comicHTMLFileGeneratorService.convertBackSlashToSlash(sourceFolderPath);
		processedComicFolderPath = comicHTMLFileGeneratorService.convertNonAsciiToUrlEcoding(processedComicFolderPath);
		processedComicFolderPath = comicHTMLFileGeneratorService.concatenateOneSlash(processedComicFolderPath);
		processedComicFolderPath = comicHTMLFileGeneratorService.addFilePrefix(processedComicFolderPath);
		
		String outputFolderPath = outputFolderPathTextField.getText();
		if(outputFolderName != null){
			outputFolderPath = outputFolderPath + "\\" + outputFolderName;
			if(!new File(outputFolderPath).mkdir()){
				System.out.println("資料夾已存在");
			}
		}		
		System.out.print(outputFolderPath + "\\" + fileName + ".html");

		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		PrintWriter pw = null;
		
		try {
			fos = new FileOutputStream(new File(outputFolderPath, fileName + ".html"), false);
			osw = new OutputStreamWriter(fos, "UTF-8");
			pw = new PrintWriter(osw);
			
			pw.print(ContinuousPictureHTMLFileGeneratorConstant.HTML_FROM_BEGIN_TO_TITLE_OF_WEB_PAGE);
			pw.print(fileName);
			pw.print(ContinuousPictureHTMLFileGeneratorConstant.HTML_FROM_TITLE_OF_WEB_PAGE_TO_AMOUNT_OF_PICTURE);
			pw.print(fileAmount);
			pw.print(ContinuousPictureHTMLFileGeneratorConstant.HTML_FROM_AMOUNT_OF_PICTURE_TO_PICTURE);
			
			for(String fileNameInForLoop : fileNameList){
				pw.print(ContinuousPictureHTMLFileGeneratorConstant.HTML_FROM_IMG_TAG_BEGIN_TO_SRC_PROPERTY);
				pw.print(processedComicFolderPath);
				pw.print(fileNameInForLoop);
				pw.print(ContinuousPictureHTMLFileGeneratorConstant.HTML_FROM_IMG_SRC_PROPERTY_TO_IMG_TAG_END);
			}
			
			pw.print(ContinuousPictureHTMLFileGeneratorConstant.HTML_FROM_PICTURE_END_TO_HTML_END);
			
			System.out.println(" OK");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally{
			if(pw != null){
				pw.close();
			}
			if(osw != null){
				try {
					osw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
	public void generateByParentFolderMode() throws UnsupportedEncodingException{
		String sourceFolderPath = sourceFolderPathTextField.getText();
		File sourceFolder = new File(sourceFolderPath);
		String[] childFolderNames = sourceFolder.list();
		
		for(String childFolderName : childFolderNames){
			generateBySingleFolderMode(sourceFolderPath + "\\" + childFolderName, null);
		}		
	}
	
	public void generateByGrandparentFolderMode() throws UnsupportedEncodingException{
		String sourceFolderPath = sourceFolderPathTextField.getText();
		File sourceFolder = new File(sourceFolderPath);
		File[] childrenFolders= sourceFolder.listFiles();
		
		for(File childFolder : childrenFolders){
			String[] grandsonFolderNames = childFolder.list();
			
			if(grandsonFolderNames == null){
				continue;
			}
			for(String grandsonFolderName : grandsonFolderNames){
				generateBySingleFolderMode(childFolder.getPath() + "\\" + grandsonFolderName, childFolder.getName()); 
			}
		}
	}
	
	private void readSetting(){
		File fileOfSetting = new File("comicHTMLFileGeneratorSetting.xml");
		try {
			contextOfJAXB = JAXBContext.newInstance(ContinuousPictureHTMLFileGeneratorSetting.class);
			if(!fileOfSetting.exists()) {				
				Marshaller marshaller = contextOfJAXB.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				
				ContinuousPictureHTMLFileGeneratorSetting defaultSetting = new ContinuousPictureHTMLFileGeneratorSetting("S", "C:\\", "C:\\");
				
				marshaller.marshal(defaultSetting, new FileOutputStream(fileOfSetting));
			}else {
				Unmarshaller unmarshaller = contextOfJAXB.createUnmarshaller();
				ContinuousPictureHTMLFileGeneratorSetting settingReadingFromXml = (ContinuousPictureHTMLFileGeneratorSetting)unmarshaller.unmarshal(fileOfSetting);
				
				if(settingReadingFromXml.getModeValue().equals(MODE_VALUE_OF_SINGLE_FOLDER_MODE)) {
					singleFolderModeRadio.setSelected(true);
				}else if(settingReadingFromXml.getModeValue().equals(MODE_VALUE_OF_PARENT_FOLDER_MODE)){
					parentFolderModeRadio.setSelected(true);
				}else{
					grandparentFolderModeRadio.setSelected(true);
				}
				String sourceFolderPath = settingReadingFromXml.getSourceFolderPath();
				sourceFolderChooser.setCurrentDirectory(new File(sourceFolderPath));
				sourceFolderPathTextField.setText(sourceFolderPath);
				String outputFolderPath = settingReadingFromXml.getOutputFolderPath();
				outputFolderChooser.setCurrentDirectory(new File(outputFolderPath));
				outputFolderPathTextField.setText(outputFolderPath);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	private void writeSettingToXml() {
		ContinuousPictureHTMLFileGeneratorSetting currentSetting = new ContinuousPictureHTMLFileGeneratorSetting();
		
		if(singleFolderModeRadio.isSelected()) {
			currentSetting.setModeValue(MODE_VALUE_OF_SINGLE_FOLDER_MODE);
		}else if(parentFolderModeRadio.isSelected()){
			currentSetting.setModeValue(MODE_VALUE_OF_PARENT_FOLDER_MODE);
		}else{
			currentSetting.setModeValue(MODE_VALUE_OF_GRANDPARENT_FOLDER_MODE);
		}
		currentSetting.setSourceFolderPath(sourceFolderChooser.getCurrentDirectory().toString());
		currentSetting.setOutputFolderPath(outputFolderChooser.getCurrentDirectory().toString());
		
		try {
			Marshaller marshaller = contextOfJAXB.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(currentSetting, new FileOutputStream("comicHTMLFileGeneratorSetting.xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
