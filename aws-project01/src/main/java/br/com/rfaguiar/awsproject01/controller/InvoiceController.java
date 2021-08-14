package br.com.rfaguiar.awsproject01.controller;

import br.com.rfaguiar.awsproject01.model.Invoice;
import br.com.rfaguiar.awsproject01.model.UrlResponse;
import br.com.rfaguiar.awsproject01.repository.InvoiceRepository;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Value("${aws.s3.bucket.invoice.name}")
    private String bucketName;
    private final AmazonS3 amazonS3;
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceController(AmazonS3 amazonS3, InvoiceRepository invoiceRepository) {
        this.amazonS3 = amazonS3;
        this.invoiceRepository = invoiceRepository;
    }

    @PostMapping
    public UrlResponse createInvoiceUrl() {
        Instant expirationTime = Instant.now().plus(Duration.ofMinutes(5));
        String processId = UUID.randomUUID().toString();
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, processId)
                .withMethod(HttpMethod.PUT)
                .withExpiration(Date.from(expirationTime));
        return new UrlResponse(expirationTime.getEpochSecond(),
                amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString());

    }

    @GetMapping
    public Iterable<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    @GetMapping("/bycustomername")
    public Iterable<Invoice> findByCustomerName(@RequestParam String customerName) {
        return invoiceRepository.findAllByCustomerName(customerName);
    }

}
