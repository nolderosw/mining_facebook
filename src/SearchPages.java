/**
 * Created by wesley150 on 10/08/17.
 */

import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Page;


public class SearchPages {

    private FacebookClient fbClient;
    private String pesquisa;
    private Connection<Page> results;

    public SearchPages(FacebookClient fbClient, String pesquisa){

        this.fbClient = fbClient;
        this.pesquisa = pesquisa;
    }
    public void createPageConnection(){
        Connection<Page> results = fbClient.fetchConnection("search", Page.class,
                Parameter.with("q", pesquisa), Parameter.with("type", "page"));

        this.results = results;
    }
    public Connection<Page> getResults(){
        return results;
    }
}
