package model.utility;

import model.entity.Event;
import model.entity.Job;
import model.entity.Post;
import model.entity.Sale;

import java.util.ArrayList;
import java.util.List;


public class GenerateId {

    private static ArrayList<String> studentList = new ArrayList<String>();

    public int generateID(ArrayList<Post> postList, String typePost) {
        String tempID = "";
        List<Integer> listofNumbers = new ArrayList<Integer>();
        for (Post post : postList) {
            if (typePost.contentEquals("Event") && post instanceof Event)
                tempID = post.getId();
            if (typePost.contentEquals("Sale") && post instanceof Sale)
                tempID = post.getId();
            if (typePost.contentEquals("Job") && post instanceof Job)
                tempID = post.getId();
            int currentInteger = Integer.parseInt(tempID.substring(3, 6));
            listofNumbers.add(currentInteger);
        }
        int max = (int) findMax(listofNumbers);
        return max + 1;
    }

    public static Integer findMax(List<Integer> list) {
        if (list == null || list.size() == 0) {
            return Integer.MIN_VALUE;
        } else {

            return Integer.MAX_VALUE;
        }
    }
}
