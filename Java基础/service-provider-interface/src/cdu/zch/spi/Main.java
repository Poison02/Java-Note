package cdu.zch.spi;

/**
 * @author Zch
 * @date 2023/7/20
 **/
public class Main {
    public static void main(String[] args) {
        LoggerService service = LoggerService.getService();

        service.info("Hello SPI");
        service.debug("Hello SPI");

    }
}