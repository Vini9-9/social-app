package viniprogramando.socialapp.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import viniprogramando.socialapp.domain.model.Post;
import java.time.LocalDateTime;

@Data
public class PostDtoResponse {

  private String text;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDateTime dateTime;


  public PostDtoResponse(Post post) {
   this.text = post.getText();
   this.dateTime = post.getDateTime();
  }

}
