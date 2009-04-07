/*
 * Process.java
 *
 * Created on January 26th by Fabricio Reis.
 */

package bean;

/**
 *
 * @author Fabricio Reis
 */
public class Process {
    private float creationTime;
    private int fathersId;
    private int id;
    private float lifeTime;
    private int priority;
    private int state; // 0 == Ready; 1 == Executing; 2 == returned from executing to ready
    private float size;
    private float turnAround;
    private float waitingTime;
    
    public Process() {        
    }
    
    public Process(Process newProcess) {
        this.creationTime = newProcess.getCreationTime();
        this.fathersId = newProcess.getFathersId();
        this.id = newProcess.getId();
        this.lifeTime = newProcess.getLifeTime();
        this.priority = newProcess.getPriority();
        this.size = newProcess.getSize();
        this.state = newProcess.getState();
        this.turnAround = newProcess.getTurnAround();
        this.waitingTime = newProcess.getWaitingTime();
    }

    public Process(float creationTime, int fathersId, int id, float lifeTime, int priority, int state, float size, float turnAround, float waitingTime) {
        this.creationTime = creationTime;
        this.fathersId = fathersId;
        this.id = id;
        this.lifeTime = lifeTime;
        this.priority = priority;
        this.state = state;
        this.size = size;
        this.turnAround = turnAround;
        this.waitingTime = waitingTime;
    }

    public float getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(float creationTime) {
        this.creationTime = creationTime;
    }    
    
    public int getFathersId() {
        return fathersId;
    }

    public void setFathersId(int fathersId) {
        this.fathersId = fathersId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(float lifeTime) {
        this.lifeTime = lifeTime;
    }    

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getTurnAround() {
        return turnAround;
    }

    public void setTurnAround(float turnAround) {
        this.turnAround = turnAround;
    }
    
    public float getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(float waitingTime) {
        this.waitingTime = waitingTime;
    }
}