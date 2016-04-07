package com.baeldung.forkjoin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class CustomRecursiveAction extends RecursiveAction {

    private String workLoad = "";

    private static final int THRESHOLD = 4;

    public CustomRecursiveAction(String workLoad) {
        this.workLoad = workLoad;
    }

    @Override
    protected void compute() {

        if(workLoad.length() > THRESHOLD) {
            ForkJoinTask.invokeAll(createSubtasks());
        } else {
           processing(workLoad);
        }
    }

    private Collection<CustomRecursiveAction> createSubtasks() {

        List<CustomRecursiveAction> subtasks =
                 new ArrayList<>();

        String partOne = workLoad.substring(0, workLoad.length()/2);
        String partTwo = workLoad.substring(workLoad.length()/2, workLoad.length());

        subtasks.add(new CustomRecursiveAction(partOne));
        subtasks.add(new CustomRecursiveAction(partTwo));

        return subtasks;
    }

    private void processing(String work) {
        work.toUpperCase();
    }
}
