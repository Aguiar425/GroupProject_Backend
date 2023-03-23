package org.mindswap.service;

import org.mindswap.dto.InvoiceDto;
import org.mindswap.exceptions.ClientNotFoundException;
import org.mindswap.exceptions.InvoiceNotFoundException;
import org.mindswap.mapper.InvoiceMapper;
import org.mindswap.model.Client;
import org.mindswap.model.Invoice;
import org.mindswap.repository.ClientRepository;
import org.mindswap.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class InvoiceServiceImpl implements InvoiceService{

    private InvoiceRepository invoiceRepository;
    private InvoiceMapper invoiceMapper;

    private ClientRepository clientRepository;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper, ClientRepository clientRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.clientRepository = clientRepository;
    }




    @Override
    public InvoiceDto getSpecificInvoice(Long invoiceID)  {
      Invoice invoice = invoiceRepository.findById(invoiceID).orElseThrow(InvoiceNotFoundException::new);
        return invoiceMapper.fromEntityToDto(invoice);
    }

    @Override
    public List<InvoiceDto> getSpecificClientInvoices(Long clientId) {
            Client client = clientRepository.findById(clientId).orElseThrow(ClientNotFoundException::new);
            List<Invoice> invoiceList = new ArrayList<>();
            client.getRentalList().forEach(r -> invoiceList.add(r.getInvoice()));
            return invoiceList.stream().map(i-> invoiceMapper.fromEntityToDto(i)).toList();
    }
}
