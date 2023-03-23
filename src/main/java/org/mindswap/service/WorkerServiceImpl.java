package org.mindswap.service;

import org.mindswap.dto.WorkerCreateDto;
import org.mindswap.dto.WorkerDto;
import org.mindswap.dto.WorkerUpdateDto;
import org.mindswap.exceptions.WorkerNotFoundException;
import org.mindswap.mapper.WorkerMapper;
import org.mindswap.model.Worker;
import org.mindswap.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Double.NaN;

@Service
public class WorkerServiceImpl implements WorkerService {
    private StaffRepository staffRepository;
    private WorkerMapper workerMapper;

    @Autowired
    public WorkerServiceImpl(StaffRepository staffRepository, WorkerMapper workerMapper) {
        this.staffRepository = staffRepository;
        this.workerMapper = workerMapper;
    }

    @Override
    public WorkerDto createWorker(WorkerCreateDto workercreateDto) {
        Worker worker = workerMapper.fromCreateDtoToEntity(workercreateDto);
        staffRepository.save(worker);
        return workerMapper.fromEntityToDto(worker);
    }

    @Override
    public WorkerDto getWorkerById(Long workerId) {
        Worker worker = staffRepository.findById(workerId).orElseThrow(WorkerNotFoundException::new);
        return workerMapper.fromEntityToDto(worker);
    }

    @Override
    public List<WorkerDto> getAllWorkers() {
        return staffRepository.findAll().stream().map(w->workerMapper.fromEntityToDto(w)).toList();

    }


    @Override
    public WorkerDto updateWorker(Long workerId, WorkerUpdateDto workerUpdateDto) {
        Worker worker = staffRepository.findById(workerId).orElseThrow(WorkerNotFoundException::new);
        if(workerUpdateDto.getFirstName() != null) {
            worker.setFirstName(workerUpdateDto.getFirstName());
        }
        if(workerUpdateDto.getLastName() != null) {
            worker.setLastName(workerUpdateDto.getLastName());
        }
        if(workerUpdateDto.getEmail() != null) {
            worker.setEmail(workerUpdateDto.getEmail());
        }
        if(!Double.isNaN(workerUpdateDto.getStoreId())) {
            worker.setStoreId(workerUpdateDto.getStoreId());
        }
        staffRepository.save(worker);
        return workerMapper.fromEntityToDto(worker);
    }
    @Override
    public void deleteWorker(Long workerID) {
        Worker worker = staffRepository.findById(workerID).orElseThrow(WorkerNotFoundException::new);
        staffRepository.delete(worker);
    }


}
