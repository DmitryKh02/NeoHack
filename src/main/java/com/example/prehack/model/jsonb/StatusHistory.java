package com.example.prehack.model.jsonb;

import com.example.prehack.model.User;
import com.example.prehack.model.enumformodel.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusHistory {

    private User user;

    private Status status;

    private LocalDateTime dateTimeChange;
}
