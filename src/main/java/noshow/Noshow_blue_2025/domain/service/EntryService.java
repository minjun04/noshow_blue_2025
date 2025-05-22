package noshow.Noshow_blue_2025.domain.service;

import lombok.RequiredArgsConstructor;
import noshow.Noshow_blue_2025.domain.repositoryInterface.StudentRepository;
import noshow.Noshow_blue_2025.infra.entity.Student;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final StudentRepository studentRepository;

    public Boolean isEntered(Student student){
        if(student.getEntry() == 1){
            return true;
        }
        else{
            return false;
        }
    }
}
