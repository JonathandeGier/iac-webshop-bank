package nl.hu.iac.webshop.bank.DTO;

public class BankConfirmationDTO {
    private Long bestellingId;
    private boolean accept;

    public BankConfirmationDTO(Long bestellingId, boolean accept) {
        this.bestellingId = bestellingId;
        this.accept = accept;
    }

    public Long getBestellingId() {
        return bestellingId;
    }

    public void setBestellingId(Long bestellingId) {
        this.bestellingId = bestellingId;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }
}
