package org.cougaar.test.knode.experiment.bette;

import org.cougaar.core.service.UIDService;
import org.cougaar.core.util.UniqueObjectBase;

/*
 * Blackboard object that holds the capture state for a clip
 */
public class ClipCaptureState
      extends UniqueObjectBase {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   public enum StateKind {
      Grabbing, Storing, Looping, Sending, NoClip
   };

   public enum CommandKind {
      StartCapture, StopCapture, Send, Clear
   };

   private long timeStamp;
   private String clipName;
   private long clipId;
   private StateKind currentState = StateKind.NoClip;
   private CommandKind outstandingCommand = null;

   public ClipCaptureState(UIDService uids, String clipName) {
      super(uids.nextUID());
      this.clipName = clipName;
      this.clipId = 0;
   }

   public synchronized String getClipName() {
      return clipName;
   }

   public synchronized void setClipName(String clipName) {
      this.clipName = clipName;
      captureTimeStamp();
   }

   public synchronized StateKind getCurrentState() {
      return currentState;
   }

   public synchronized void setCurrentState(StateKind currentState) {
      this.currentState = currentState;
      captureTimeStamp();
   }

   public synchronized CommandKind getOutstandingCommand() {
      return outstandingCommand;
   }

   public synchronized void setOutstandingCommand(CommandKind outstandingCommand) {
      this.outstandingCommand = outstandingCommand;
      if (outstandingCommand != null && outstandingCommand.equals(CommandKind.StartCapture)) {
         this.clipId = System.currentTimeMillis(); // Timestamp more unique than
                                                   // incrementing a counter
      }
      captureTimeStamp();
   }

   public synchronized long getTimeStamp() {
      return timeStamp;
   }

   private void captureTimeStamp() {
      this.timeStamp = System.currentTimeMillis();
   }

   public long getClipId() {
      return clipId;
   }

}
