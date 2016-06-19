package conversationWindow;

import javafx.scene.control.TextArea;

/**
 * Created by marcin on 19/06/2016.
 */
public class ConversationsTextAreas {
    private final int NUMBER_OF_CONVERSATION_WINDOWS = 100;
    TextArea[] conversationTextAreas;
    public ConversationsTextAreas(){
        System.out.println("array of text area created");
        conversationTextAreas = new TextArea[NUMBER_OF_CONVERSATION_WINDOWS];
        initializeTextAreas();
    }
    public void initializeTextAreas(){
        for(int i=0;i<NUMBER_OF_CONVERSATION_WINDOWS;i++){
            conversationTextAreas[i] = new TextArea("");
        }
    }
    public void addTextToConversation(String text, int position){
        conversationTextAreas[position].appendText(text);
    }
    public TextArea getConversationTextArea(int position){
        return conversationTextAreas[position];
    }
}
