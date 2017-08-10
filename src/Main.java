import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String pesquisa_pagina, id_pagina_escolhida;
        int escolha_pagina;
        List<String> ids_paginas = new ArrayList<>();

        Scanner in = new Scanner(System.in);

        Autenticadora autenticadora = new Autenticadora("1788401691451796","968d4a0564aa0ee349356b9b194dc945");

        FacebookClient fbClient = new DefaultFacebookClient(autenticadora.gettoken(), Version.LATEST);

        /*Page page = fbClient.fetchObject("McDonaldsBrasil", Page.class);

        Connection <Post> postFeed = fbClient.fetchConnection(page.getId()+"/feed",Post.class);


        for (List<Post> postPage: postFeed){
            for (Post aPost: postPage){
                    if(aPost.getId().equals("287927677912456_1525950374110174")) {
                        System.out.println("Mensagem do POST: " + aPost.getMessage());
                        System.out.println("fb.com/" + aPost.getId());
                    }
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

        }*/
        System.out.println("Digite uma pagina que deseja extrair opiniões:");
        pesquisa_pagina = in.next();
        SearchPages pages = new SearchPages(fbClient,pesquisa_pagina);
        pages.createPageConnection();
        int cont_pages = 0;
        for (List<Page> pageFeed: pages.getResults()){
            for (Page aPage: pageFeed){
                System.out.println(cont_pages + " - "+ aPage.getName() + " ("+"fb.com/"+aPage.getId()+")"+"\n");
                cont_pages ++;
                ids_paginas.add(aPage.getId());
            }
        }
        System.out.println("Foram encontradas "+ cont_pages + " paginas relacionadas com sua entrada.\n");
        System.out.println("Digite um número correspondente a uma das páginas que deseja buscar um POST");

        escolha_pagina = in.nextInt();
        id_pagina_escolhida = ids_paginas.get(escolha_pagina);
        SearchPosts posts = new SearchPosts(fbClient);
        posts.createPostConnection(id_pagina_escolhida);

        System.out.println("Posts da pagina escolhida: ");
        int cont_posts = 0;
        for (List<Post> postPage: posts.getResults()) {
            for (Post aPost : postPage) {
                System.out.println("Post "+ cont_posts);
                System.out.println("Mensagem do Post: " + aPost.getMessage());
                System.out.println("Link do Post: fb.com/" + aPost.getId()+"\n");
                cont_posts ++;
            }
        }
        System.out.println("Escolha um dos " + cont_posts + " Posts da página");


    }
}