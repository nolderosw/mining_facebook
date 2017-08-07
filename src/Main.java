import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String acessToken = "EAAZAaireI4ZAQBAKUC2PBLfSjFlPNYlSjahSpNsDaGJKcPjTwRqwYZC2CkC3KxOW4EDiulvwoIYHbFvJUwYOwE7WtccWnmIZAOcm0aav24Q8s3vlQfCapYq7K8YKdfmdhyGZBZAg1mfBEIB8vC6yZCYOKjlHBbA0JDZBty3HMQsPoEZCgvH2Y4GxmQxseQnKEjZBQZD";

        FacebookClient fbClient = new DefaultFacebookClient(acessToken);

       /* Connection<Post> result = fbClient.fetchConnection("me/feed", Post.class);

        for (List<Post> page: result){
            for (Post aPost: page){
                System.out.println(aPost.getMessage());
                System.out.println("fb.com/"+aPost.getId());
            }

        }*/
        Page page = fbClient.fetchObject("SamsungMobile", Page.class);

        Connection <Post> postFeed = fbClient.fetchConnection(page.getId()+"/feed",Post.class);

        List<Comment> comments=new ArrayList<>();
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
    }


}