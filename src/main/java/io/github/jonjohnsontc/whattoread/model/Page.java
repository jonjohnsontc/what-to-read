package io.github.jonjohnsontc.whattoread.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Data
@Component
public class Page {
    String description;
    String title;
}
