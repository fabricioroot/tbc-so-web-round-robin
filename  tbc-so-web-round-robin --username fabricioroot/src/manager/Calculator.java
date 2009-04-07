package manager;

import java.util.Vector;
import bean.Process;

/**
 *
 * @author Fabricio Reis
 */
public class Calculator {
    
    public Calculator() {
    }
    
    /* This method calculates the sum of the burst times.
     */
    public float burstTimeSum (Vector<Process> processes, float quantum) {
        float aux = 0;

        for (int i = 0; i <= (processes.size() - 1); i++) {
            if((processes.elementAt(i).getLifeTime() > quantum) && (quantum > 0)) {
                aux += quantum;
            }
            else{
                aux += processes.elementAt(i).getLifeTime();
            }
        }
        return aux;
    }

    /* This method calculates the waiting times of 'Vector<Process> processes' and return it.
     */
    public Vector<Process> refreshesWaitingTimesAndTurnArounds (Vector<Process> processes, float quantum) {
        float aux = 0;
        Vector<Process> out = new Vector<Process>(processes);
        
        for(int i = 0; i <= (processes.size() - 1); i++) {
            for (int j = 0; j < i; j++) {
                if (processes.elementAt(j).getLifeTime() > quantum) {
                    aux += quantum;
                }
                else {
                    aux += processes.elementAt(j).getLifeTime();
                }
            }
            out.elementAt(i).setWaitingTime(aux);
            out.elementAt(i).setTurnAround(out.elementAt(i).getWaitingTime() + out.elementAt(i).getSize());
            aux = 0;
        }
        return out;
    }

    /* This method calculates the average of the waiting times.
     */
    public float averageWaitingTime (Vector<Process> processes) {
        float aux = 0;
        for (int i = 0; i <= (processes.size() - 1); i++) {
            aux += processes.elementAt(i).getWaitingTime();
        }
        aux = aux / processes.size();
        return aux;
    }
    
    /* This method calculates the average of the turns around.
     */
    public float averageTurnAround (Vector<Process> processes) {
        float aux = 0;
        for (int i = 0; i <= (processes.size() - 1); i++) {
            aux += processes.elementAt(i).getTurnAround();
        }
        aux = aux / processes.size();
        return aux;
    }
}