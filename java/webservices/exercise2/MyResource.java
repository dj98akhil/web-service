package webservices.exercise2;

import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dao.PostDAOImpl;
import model.Post;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

	PostDAOImpl impl = new PostDAOImpl();

	@Path("get")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Post> getAll() {
		ArrayList<Post> list = (ArrayList<Post>) impl.view();
		return list;

	}

	@Path("add")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Post insert(Post newPost) {

		newPost = impl.insert(newPost);

		return newPost;
	}

	@Path("put")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Post putIt(Post editedPost) {
		impl.update(editedPost);
		return editedPost;
	}

	@Path("delete/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteIt(@PathParam("id") int id) {
		int res =impl.delete(id);
		if(res==1) {
			return "Deleted Successfully id:"+id;
		}
		return "Delete Failed id:" + id;
	}
}
