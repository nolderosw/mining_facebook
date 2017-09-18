import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;




public class Main {

    public static void main(String[] args) {

        String pesquisa_pagina, id_pagina_escolhida, id_post_escolhido, collection_name;
        int escolha_pagina, escolha_post, menu = 3;
        List<String> ids_paginas = new ArrayList<>();
        List<String> ids_posts = new ArrayList<>();

        Scanner in = new Scanner(System.in);

        Autenticadora autenticadora = new Autenticadora("1788401691451796","968d4a0564aa0ee349356b9b194dc945");

        FacebookClient fbClient = new DefaultFacebookClient(autenticadora.gettoken(), Version.LATEST);

        while (menu != 0) {

            System.out.println("Olá, selecione uma opção: ");
            System.out.println("1 - Criar uma Base de Dados");
            System.out.println("2 - Analisar uma Base de Dados já criada");
            System.out.println("0 - Sair");
            menu = in.nextInt();
            if(menu == 1) {
                //conectando-se com BD
                MongoClient mongo = new MongoClient("localhost", 27017);
                MongoDatabase database = mongo.getDatabase("FB_Mining_App");

                //criando base de dados
                System.out.println("Digite o nome da base de dados (Se o nome ja existir base será reescrita)");
                collection_name = in.next();

                try {
                    database.getCollection(collection_name);
                }
                catch (Exception e){
                    database.createCollection(collection_name);
                }
                MongoCollection<Document> collection = database.getCollection(collection_name);


                //Pesquisando a pagina do face
                System.out.println("Pesquise sobre a página desejada:");
                pesquisa_pagina = in.next();
                SearchPages pages = new SearchPages(fbClient, pesquisa_pagina);
                pages.createPageConnection();
                int cont_pages = 0;
                for (List<Page> pageFeed : pages.getResults()) {
                    for (Page aPage : pageFeed) {
                        System.out.println(cont_pages + " - " + aPage.getName() + " (" + "fb.com/" + aPage.getId() + ")" + "\n");
                        cont_pages++;
                        ids_paginas.add(aPage.getId());
                    }
                }
                System.out.println("Foram encontrados " + cont_pages + " resultados possíveis");
                System.out.println("Digite o número da página que deseja criar a base de dados");

                escolha_pagina = in.nextInt();
                id_pagina_escolhida = ids_paginas.get(escolha_pagina);
                SearchPosts posts = new SearchPosts(fbClient);
                posts.createPostConnection(id_pagina_escolhida);

                int cont_posts = 0;
                boolean post_limit = false;
                for (List<Post> postPage : posts.getResults()) {
                    for (Post aPost : postPage) {
                        System.out.println("Post " + cont_posts);
                        System.out.println("Mensagem do Post: " + aPost.getMessage());
                        System.out.println("Link do Post: fb.com/" + aPost.getId() + "\n");
                        cont_posts++;
                        ids_posts.add(aPost.getId());
                        if (cont_posts > 100) {
                            post_limit = true;
                            break;
                        }
                    }
                    if (post_limit) {
                        break;
                    }
                }

                System.out.println("Escolha um dos Posts acima para criar a base de dados");
                escolha_post = in.nextInt();
                id_post_escolhido = ids_posts.get(escolha_post);
                SearchComments comments = new SearchComments(fbClient);
                comments.createCommentConnection(id_post_escolhido);

                int cont_comments = 0;
                for (List<Comment> commentPage : comments.getResults()) {
                    for (Comment comment : commentPage) {
                        Document comments_doc = new Document("Mensagem", comment.getMessage())
                                .append("Usuario", comment.getFrom().getName())
                                .append("Data", comment.getCreatedTime())
                                .append("Link", "fb.com/"+comment.getId());
                        collection.insertOne(comments_doc);
                    }
                }
                System.out.println("Base de dados Criada!");
            }
            else if(menu == 2){
                System.out.println("Digite o nome da base de dados");
            }
            else if(menu == 0){
                System.out.println("Obrigado!");
            }
            else{
                System.out.println("Entrada Inválida!");
            }
        }

    }
}