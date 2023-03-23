package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "subjects")
@Getter @Setter @ToString
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String courseCode;

    private String studentIds;

    private String teacherId;
    private LocalDate time;

    @Enumerated(EnumType.STRING)
    private WeekDay weekDay;

    public enum WeekDay{
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY;
    }

    public List<String> getStudentIds() {
        if (!StringUtils.isEmpty(studentIds)) {
            return Arrays.stream(studentIds.split(",")).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public void setStudentIds(List<String> mediumBankCurrenciesStr) {
        if (mediumBankCurrenciesStr != null) {
            studentIds = String.join("," , mediumBankCurrenciesStr);
        }
    }
}