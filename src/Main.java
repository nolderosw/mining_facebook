import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.*;
import javax.swing.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Autenticadora autenticadora = new Autenticadora("1788401691451796","968d4a0564aa0ee349356b9b194dc945");

        FacebookClient fbClient = new DefaultFacebookClient(autenticadora.gettoken(), Version.LATEST);


       /* Connection<Post> result = fbClient.fetchConnection("me/feed", Post.class);

        for (List<Post> page: result){
            for (Post aPost: page){
                System.out.println(aPost.getMessage());
                System.out.println("fb.com/"+aPost.getId());
            }

        }*/
        Page page = fbClient.fetchObject("SamsungMobile", Page.class);

        Connection <Post> postFeed = fbClient.fetchConnection(page.getId()+"/feed",Post.class);


        for (List<Post> postPage: postFeed){
            for (Post aPost: postPage){
                if(aPost.getId().equals("114219621960016_1430463993668899")) {
                    System.out.println("Mensagem do POST: " + aPost.getMessage());
                    System.out.println("Comentários: \n");
                    Connection<Comment> commentConnection = fbClient.fetchConnection("114219621960016_1430463993668899/comments", Comment.class);
                    for (List<Comment> commentPage : commentConnection) {
                        for (Comment comment : commentPage) {
                            System.out.println("Usuario: " + comment.getFrom().getName() + "("+"fb.com/"+comment.getFrom().getId()+")");
                            System.out.println("Comentário: " + comment.getMessage());
                            System.out.println("Criado: " + comment.getCreatedTime());
                            System.out.println("----------------------------------------");

                        }
                    }
                }
            }

        }
        /*JFrame frame = new JFrame("Autenticar");
        frame.setContentPane(new autenticar().panel_autenticar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);*/

    }


}