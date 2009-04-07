package thread;

import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import bean.Process;
import gui.MainScreen;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import manager.Calculator;

/**
 *
 * @author Fabrício Reis
 */
public class AlgorithmStepsThread implements Runnable {
    
    MainScreen mainScreen;
    JButton jButtonAlgorithmSteps;
    JButton jButtonReport; 
    Vector<Process> processesList;
    Vector<Process> reportBase;
    Vector<Process> reportBaseTemp;
    int timeCounter;
    JPanel jPanelCPU;
    JProgressBar jProgressBarExecution;
    JLabel jLabelShowBurstTime;
    JLabel jLabelShowCreationTime;
    JLabel jLabelTimeCounter;
    JLabel jLabelCPU;
    boolean isJButtonOkClicked = false;
    JDialog jDialogNextStep;
    JButton jButtonOkNextStep;
    JLabel jLabelAtDialogNextStep;
    float remainingTimeToFinishRunning;
    float quantum;
    Calculator calculator = new Calculator();

    public AlgorithmStepsThread(MainScreen mainScreen, JButton jButtonAlgorithmSteps, JButton jButtonReport, Vector<Process> processesList, Vector<Process> reportBase,
                                int timeCounter, JPanel jPanelCPU, JProgressBar jProgressBarExecution, JLabel jLabelShowBurstTime, JLabel jLabelShowCreationTime,
                                JLabel jLabelTimeCounter, JLabel jLabelCPU, float quantum){
        
        this.mainScreen = mainScreen;
        this.jButtonAlgorithmSteps = jButtonAlgorithmSteps;
        this.jButtonReport = jButtonReport;
        this.processesList = processesList;
        this.reportBase = reportBase;
        this.timeCounter = timeCounter;
        this.jPanelCPU = jPanelCPU;
        this.jProgressBarExecution = jProgressBarExecution;
        this.jLabelShowBurstTime = jLabelShowBurstTime;
        this.jLabelShowCreationTime = jLabelShowCreationTime;
        this.jLabelTimeCounter = jLabelTimeCounter;
        this.jLabelCPU = jLabelCPU;
        this.quantum = quantum;
    }

    public Vector<Process> getProcessesList() {
        return processesList;
    }
    
    public Vector<Process> getReportBase() {
        return reportBase;
    }

    public int getTimeCounter() {
        return timeCounter;
    }

    public float getRemainingTimeToFinishRunning() {
        return remainingTimeToFinishRunning;
    }
    
    public JDialog getJDialogNextStep() {
        return jDialogNextStep;
    }

    public void setJDialogNextStep(JDialog jDialogNextStep) {
        this.jDialogNextStep = jDialogNextStep;
    }
    
    public void run() {
        this.jButtonAlgorithmSteps.setEnabled(false);
        
        if (!this.processesList.isEmpty()) {
            
            Process process = new Process();
            process = this.processesList.firstElement();
            this.processesList.remove(0);
            this.mainScreen.paintProcessesList(this.processesList);
            
            if(this.reportBase == null) {
                this.reportBase = new Vector<Process>();
            }
            
            int position = -1;
            for (int k = 0; k <= (this.reportBase.size() - 1); k++) {
                if (process.getId() == reportBase.elementAt(k).getId()) {
                    position = k;
                    k = this.reportBase.size();
                }
            }
            
            if (position != -1) {
                this.reportBase.setElementAt(process, position);
            }
            else {
                this.reportBase.add(process);
            }

            JTextField block = new JTextField();
            block.setBackground(new java.awt.Color(255, 51, 0));
            block.setForeground(new java.awt.Color(0, 0, 0));
            block.setHorizontalAlignment(javax.swing.JTextField.CENTER);
            block.setEditable(false);
            block.setBounds(35, 20, 30, 30);
            this.jPanelCPU.add(block);
            block.setText("P" + String.valueOf(process.getId()));
            block.setToolTipText("Tempo de burst = " + String.valueOf((int)process.getLifeTime()));
            this.jProgressBarExecution.setVisible(true);
            this.jLabelShowBurstTime.setVisible(true);
            this.jLabelShowBurstTime.setText("Tempo de burst de P" + String.valueOf(process.getId()) + " = " + String.valueOf((int)process.getLifeTime()));
            this.jLabelShowCreationTime.setVisible(true);
            this.jLabelShowCreationTime.setText("Tempo na criação de P" + String.valueOf(process.getId()) + " = " + String.valueOf((int)process.getCreationTime()));

            this.jDialogNextStep = new JDialog();
            this.jDialogNextStep.setModalityType(ModalityType.MODELESS);
            this.jDialogNextStep.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
            //this.jDialogNextStep.setAlwaysOnTop(true);
            this.jDialogNextStep.setResizable(false);
            this.jDialogNextStep.setBounds(600, 520, 231, 118);
            this.jDialogNextStep.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            this.jDialogNextStep.setLayout(null);
            this.jDialogNextStep.setTitle("EXECUÇÃO DE P" + String.valueOf(process.getId()) + " ...");

            this.jButtonOkNextStep = new JButton("OK");
            this.jButtonOkNextStep.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            this.jButtonOkNextStep.setBorderPainted(true);
            this.jButtonOkNextStep.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            this.jButtonOkNextStep.setBounds(80, 35, 60, 30);

            this.jButtonOkNextStep.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    isJButtonOkClicked = true;
                }
            });

            this.jLabelAtDialogNextStep = new JLabel("Clique em 'OK' para o próximo passo");
            this.jLabelAtDialogNextStep.setBounds(5, 3, 500, 30);

            this.jDialogNextStep.add(this.jLabelAtDialogNextStep);
            this.jDialogNextStep.add(this.jButtonOkNextStep);
            
            this.jDialogNextStep.setVisible(true);
            
            if (process.getLifeTime() == this.quantum) {
                this.remainingTimeToFinishRunning = process.getLifeTime();
                int j = 0;
                int aux = 0;
                while (j <= (process.getLifeTime() - 1)) {
                    if (this.isJButtonOkClicked) {
                        this.isJButtonOkClicked = false;
                        this.remainingTimeToFinishRunning--;
                        j++;
                        aux = 100 / (int)process.getLifeTime();
                        this.timeCounter++;
                        this.jLabelTimeCounter.setText(String.valueOf(this.timeCounter));
                        this.jProgressBarExecution.setValue(j*aux);
                        this.jProgressBarExecution.getUI().update(this.jProgressBarExecution.getGraphics(), this.jProgressBarExecution);
                    }
                }

                // This bit is used to show to the user the last interaction (when 'jProgressBarExecution' is 100%) without increase 'timeCounter'
                while (j == process.getLifeTime()) {
                    if (this.isJButtonOkClicked) {
                        this.isJButtonOkClicked = false;
                        aux = 100 / (int)process.getLifeTime();
                        j++;
                        this.jProgressBarExecution.setValue(j*aux);
                        this.jProgressBarExecution.getUI().update(this.jProgressBarExecution.getGraphics(), this.jProgressBarExecution);
                    }
                }

                this.jDialogNextStep.setVisible(false);

                this.jPanelCPU.removeAll();
                this.jPanelCPU.repaint();
                this.jPanelCPU.add(jLabelCPU);
                this.jButtonReport.setEnabled(true);
                this.jProgressBarExecution.setVisible(false);
                this.jLabelShowBurstTime.setText("");
                this.jLabelShowCreationTime.setText("");

                if(this.processesList.size() > 0) {
                    this.jButtonAlgorithmSteps.setEnabled(true);
                }
            }
            else {
                if (process.getLifeTime() > this.quantum) {
                    this.remainingTimeToFinishRunning = process.getLifeTime();
                    int j = 0;
                    int aux = 0;
                    while (j <= (this.quantum - 1)) {
                        if (this.isJButtonOkClicked) {
                            this.isJButtonOkClicked = false;
                            this.remainingTimeToFinishRunning--;
                            j++;
                            aux = 100 / (int)this.quantum;
                            this.timeCounter++;
                            this.jLabelTimeCounter.setText(String.valueOf(this.timeCounter));
                            this.jProgressBarExecution.setValue(j*aux);
                            this.jProgressBarExecution.getUI().update(this.jProgressBarExecution.getGraphics(), this.jProgressBarExecution);
                        }
                    }

                    // This bit is used to show to the user the last interaction (when 'jProgressBarExecution' is 100%) without increase 'timeCounter'
                    while (j == this.quantum) {
                        if (this.isJButtonOkClicked) {
                            this.isJButtonOkClicked = false;
                            aux = 100 / (int)this.quantum;
                            j++;
                            this.jProgressBarExecution.setValue(j*aux);
                            this.jProgressBarExecution.getUI().update(this.jProgressBarExecution.getGraphics(), this.jProgressBarExecution);
                        }
                    }

                    this.jDialogNextStep.setVisible(false);

                    this.jPanelCPU.removeAll();
                    this.jPanelCPU.repaint();
                    this.jPanelCPU.add(jLabelCPU);
                    
                    this.reportBase.removeElement(process);
                    
                    process.setLifeTime(this.remainingTimeToFinishRunning);
                    process.setWaitingTime(process.getWaitingTime() + this.calculator.burstTimeSum(this.processesList, this.quantum));
                    process.setTurnAround(process.getWaitingTime() + process.getSize());  
                    process.setState(2);
                    this.processesList.add(process);
                    
                    JOptionPane.showMessageDialog(null, "O tempo de burst de P" + process.getId() + " = " + (int)process.getSize() + " é maior que o quantum = " + (int)this.quantum + ". \n" +
                            "Por isso, P" + process.getId() + " entrará na última posição da lista de processos prontos com tempo de burst restante = " + (int)process.getLifeTime() + ".", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
                    
                    this.mainScreen.paintProcessesList(this.processesList);
                    
                    this.jButtonReport.setEnabled(true);
                    this.jProgressBarExecution.setVisible(false);
                    this.jLabelShowBurstTime.setText("");
                    this.jLabelShowCreationTime.setText("");

                    if(this.processesList.size() > 0) {
                        this.jButtonAlgorithmSteps.setEnabled(true);
                    }
                }
                else {
                    // process.getLifeTime() < this.quantum
                    this.remainingTimeToFinishRunning = process.getLifeTime();
                    int j = 0;
                    int aux = 0;
                    while (j <= (process.getLifeTime() - 1)) {
                        if (this.isJButtonOkClicked) {
                            this.isJButtonOkClicked = false;
                            this.remainingTimeToFinishRunning--;
                            j++;
                            aux = 100 / (int)process.getLifeTime();
                            this.timeCounter++;
                            this.jLabelTimeCounter.setText(String.valueOf(this.timeCounter));
                            this.jProgressBarExecution.setValue(j*aux);
                            this.jProgressBarExecution.getUI().update(this.jProgressBarExecution.getGraphics(), this.jProgressBarExecution);
                        }
                    }

                    // This bit is used to show to the user the last interaction (when 'jProgressBarExecution' is 100%) without increase 'timeCounter'
                    while (j == process.getLifeTime()) {
                        if (this.isJButtonOkClicked) {
                            this.isJButtonOkClicked = false;
                            aux = 100 / (int)process.getLifeTime();
                            j++;
                            this.jProgressBarExecution.setValue(j*aux);
                            this.jProgressBarExecution.getUI().update(this.jProgressBarExecution.getGraphics(), this.jProgressBarExecution);
                        }
                    }

                    this.jDialogNextStep.setVisible(false);

                    this.jPanelCPU.removeAll();
                    this.jPanelCPU.repaint();
                    this.jPanelCPU.add(jLabelCPU);
                    this.jButtonReport.setEnabled(true);
                    this.jProgressBarExecution.setVisible(false);
                    this.jLabelShowBurstTime.setText("");
                    this.jLabelShowCreationTime.setText("");

                    if(this.processesList.size() > 0) {
                        this.jButtonAlgorithmSteps.setEnabled(true);
                    }
                }
            }
            this.remainingTimeToFinishRunning = 0;
        }
        else {
            JOptionPane.showMessageDialog(null, "Não há processos na lista de processos prontos!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        }
    }
}
