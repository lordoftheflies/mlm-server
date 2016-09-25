/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam;

import hu.cherubits.wonderjam.common.ContentType;
import hu.cherubits.wonderjam.dal.AccountRepository;
import hu.cherubits.wonderjam.dal.ContainerContentRepository;
import hu.cherubits.wonderjam.dal.ContentRepository;
import hu.cherubits.wonderjam.dal.MailBoxRepository;
import hu.cherubits.wonderjam.dal.MessageRepository;
import hu.cherubits.wonderjam.dal.NetworkTreeRepository;
import hu.cherubits.wonderjam.entities.AccountEntity;
import hu.cherubits.wonderjam.entities.ContainerContentEntity;
import hu.cherubits.wonderjam.entities.ContentEntity;
import hu.cherubits.wonderjam.entities.ImageContentEntity;
import hu.cherubits.wonderjam.entities.MailBoxEntity;
import hu.cherubits.wonderjam.entities.NetworkNodeEntity;
import hu.cherubits.wonderjam.entities.NetworkNodeType;
import hu.cherubits.wonderjam.entities.ReferenceContentEntity;
import hu.cherubits.wonderjam.entities.TextContentEntity;
import hu.cherubits.wonderjam.entities.VideoContentEntity;
import java.util.UUID;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author lordoftheflies
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TopFlavonContentTest extends ChristeamServerApplicationTests {

//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @Autowired
//    private ContentManagementService contentManagementServiceMock;
//    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
//            MediaType.APPLICATION_JSON.getSubtype(),
//            Charset.forName("utf8"));
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private ContainerContentRepository containerContentRepository;

    @Autowired
    private NetworkTreeRepository networkRepo;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MailBoxRepository mailBoxRepository;

    @Test
    public void atestCleanUp() {
        messageRepository.deleteAll();
        contentRepository.deleteAll();
        containerContentRepository.deleteAll();
        accountRepo.deleteAll();
        mailBoxRepository.deleteAll();
        networkRepo.deleteAll();
    }

    private NetworkNodeEntity heglasNode;

    private NetworkNodeEntity balazspeczelyNode;

    @Test
    public void btestNetwork() {

        AccountEntity heglas = new AccountEntity();
        heglas.setEmail("heglas11@gmail.com");
        heglas.setName("Teszt adminisztrátor");
        heglas.setPassword("qwe123");
        heglas.setPreferredLanguage("en");
        heglas = accountRepo.save(heglas);
        heglasNode = new NetworkNodeEntity();
        heglasNode.setActive(true);
        heglasNode.setCodes(1);
        heglas.setState(NetworkNodeType.ADMIN);
        heglasNode = networkRepo.save(heglasNode);
        heglas.setNode(heglasNode);
        heglas = accountRepo.save(heglas);
        MailBoxEntity heglasMb = new MailBoxEntity();
        heglasMb.setOwner(heglasNode);
        mailBoxRepository.save(heglasMb);

        AccountEntity balazspeczely = new AccountEntity();
        balazspeczely.setEmail("laszlo.hegedus@cherubits.hu");
        balazspeczely.setName("Teszt felhasználó");
        balazspeczely.setPassword("qwe123");
        balazspeczely.setPreferredLanguage("hu");
        balazspeczely = accountRepo.save(balazspeczely);
        balazspeczelyNode = new NetworkNodeEntity();
        balazspeczelyNode.setActive(true);
        balazspeczelyNode.setCodes(2);
        balazspeczely.setState(NetworkNodeType.ADMIN);
        balazspeczelyNode = networkRepo.save(balazspeczelyNode);
        balazspeczely.setNode(balazspeczelyNode);
        balazspeczely = accountRepo.save(balazspeczely);
        MailBoxEntity balazspeczelyMb = new MailBoxEntity();
        balazspeczelyMb.setOwner(balazspeczelyNode);
        mailBoxRepository.save(balazspeczelyMb);

        AccountEntity cseszkupopoveszku = new AccountEntity();
        cseszkupopoveszku.setEmail("cseszku@popoveszku.com");
        cseszkupopoveszku.setName("Cseszku popoveszku");
        cseszkupopoveszku.setPassword("qwe123");
        cseszkupopoveszku.setPreferredLanguage("po");
        cseszkupopoveszku = accountRepo.save(cseszkupopoveszku);
        NetworkNodeEntity cseszkupopoveszkuNode = new NetworkNodeEntity();
        cseszkupopoveszku.setState(NetworkNodeType.USER);
        cseszkupopoveszkuNode.setActive(true);
        cseszkupopoveszkuNode.setCodes(3);
        cseszkupopoveszkuNode = networkRepo.save(cseszkupopoveszkuNode);
        cseszkupopoveszku.setNode(cseszkupopoveszkuNode);
        cseszkupopoveszku = accountRepo.save(cseszkupopoveszku);
        MailBoxEntity cseszkupopoveszkuMb = new MailBoxEntity();
        cseszkupopoveszkuMb.setOwner(cseszkupopoveszkuNode);
        mailBoxRepository.save(cseszkupopoveszkuMb);

        AccountEntity parazita = new AccountEntity();
        parazita.setEmail("zita.para@gmail.com");
        parazita.setName("Para Zita");
        parazita.setPassword("qwe123");
        parazita.setPreferredLanguage("hu");
        parazita = accountRepo.save(parazita);
        NetworkNodeEntity parazitaNode = new NetworkNodeEntity();
        parazitaNode.setActive(true);
        parazitaNode.setCodes(4);
        parazita.setState(NetworkNodeType.USER);
        parazitaNode = networkRepo.save(parazitaNode);
        parazita.setNode(parazitaNode);
        parazita = accountRepo.save(parazita);
        MailBoxEntity parazitaMb = new MailBoxEntity();
        parazitaMb.setOwner(parazitaNode);
        mailBoxRepository.save(parazitaMb);

        AccountEntity tesztelek = new AccountEntity();
        tesztelek.setEmail("teszt.elek@gmail.com");
        tesztelek.setName("Teszt Elek");
        tesztelek.setPassword("qwe123");
        tesztelek.setPreferredLanguage("hu");
        tesztelek = accountRepo.save(tesztelek);
        NetworkNodeEntity tesztelekNode = new NetworkNodeEntity();
        tesztelekNode.setActive(true);
        tesztelek.setState(NetworkNodeType.USER);
        tesztelekNode.setCodes(5);
        tesztelekNode = networkRepo.save(tesztelekNode);
        tesztelek.setNode(tesztelekNode);
        tesztelek = accountRepo.save(tesztelek);
        MailBoxEntity tesztelekMb = new MailBoxEntity();
        tesztelekMb.setOwner(tesztelekNode);
        mailBoxRepository.save(tesztelekMb);

        AccountEntity feriahegyrol = new AccountEntity();
        feriahegyrol.setEmail("feriahegyrol@gmail.com");
        feriahegyrol.setName("Ferdinand Highlander");
        feriahegyrol.setPassword("qwe123");
        feriahegyrol = accountRepo.save(feriahegyrol);
        feriahegyrol.setPreferredLanguage("en");
        NetworkNodeEntity feriahegyrolNode = new NetworkNodeEntity();
        feriahegyrolNode.setActive(true);
        feriahegyrol.setState(NetworkNodeType.USER);
        feriahegyrolNode.setCodes(6);
        feriahegyrolNode = networkRepo.save(feriahegyrolNode);
        feriahegyrol.setNode(feriahegyrolNode);
        feriahegyrol = accountRepo.save(feriahegyrol);
        MailBoxEntity feriahegyrolMb = new MailBoxEntity();
        feriahegyrolMb.setOwner(feriahegyrolNode);
        mailBoxRepository.save(feriahegyrolMb);

        AccountEntity romanok = new AccountEntity();
        romanok.setName("Románia");
        romanok = accountRepo.save(romanok);
        NetworkNodeEntity romanokNode = new NetworkNodeEntity();
        romanokNode.setActive(true);
        romanokNode.setCodes(7);
        romanok.setState(NetworkNodeType.GROUP);
        romanokNode = networkRepo.save(romanokNode);
        romanok.setNode(romanokNode);
        romanok = accountRepo.save(romanok);

        AccountEntity gorogok = new AccountEntity();
        gorogok.setName("Görögország");
        gorogok = accountRepo.save(gorogok);
        NetworkNodeEntity gorogokNode = new NetworkNodeEntity();
        gorogokNode.setActive(true);
        gorogokNode.setCodes(8);
        gorogokNode = networkRepo.save(gorogokNode);
        gorogok.setNode(gorogokNode);
        gorogok.setState(NetworkNodeType.GROUP);
        gorogok = accountRepo.save(gorogok);

        AccountEntity magyarok = new AccountEntity();
        magyarok.setName("Magyarország");
        magyarok = accountRepo.save(magyarok);
        NetworkNodeEntity magyarokNode = new NetworkNodeEntity();
        magyarokNode.setActive(true);
        magyarokNode.setCodes(9);
        magyarok.setState(NetworkNodeType.GROUP);
        magyarokNode = networkRepo.save(magyarokNode);
        magyarok.setNode(magyarokNode);
        magyarok = accountRepo.save(magyarok);

        magyarokNode.setParent(heglasNode);
        networkRepo.save(magyarokNode);
        balazspeczelyNode.setParent(heglasNode);
        networkRepo.save(balazspeczelyNode);
        romanokNode.setParent(heglasNode);
        networkRepo.save(romanokNode);
        cseszkupopoveszkuNode.setParent(romanokNode);
        networkRepo.save(cseszkupopoveszkuNode);
        gorogokNode.setParent(balazspeczelyNode);
        networkRepo.save(gorogokNode);
        tesztelekNode.setParent(balazspeczelyNode);
        networkRepo.save(tesztelekNode);
        feriahegyrolNode.setParent(balazspeczelyNode);
        networkRepo.save(feriahegyrolNode);
        parazitaNode.setParent(balazspeczelyNode);
        networkRepo.save(parazitaNode);

    }

    /**
     * Test of publish method, of class ContentManagementService.
     */
    @Test
    public void ctestCustomArticle() throws Exception {
        System.out.println("publish");

        ContainerContentEntity flavonActiveContainer = new ContainerContentEntity();
        flavonActiveContainer.setContentType(ContentType.ASSEMBLED);
        flavonActiveContainer.setDraft(false);
        flavonActiveContainer.setNode(heglasNode);
        flavonActiveContainer.setPublicIndicator(true);
        flavonActiveContainer.setTitle("FLAVON ACTIVE (HU)");
        flavonActiveContainer = containerContentRepository.save(flavonActiveContainer);

        TextContentEntity flavonActiveParagraph0 = new TextContentEntity();
        flavonActiveParagraph0.setContent("Ha talpon akarunk maradni, ha meg akarunk felelni a XXI. század elvárásainak és kihívásainak, ha lépést szeretnénk tartani a rohanó mindennapokkal, akkor mindenképpen tudatosan és aktívan kell élnünk. Ez a kihívás mindannyiunkat érint.");
        flavonActiveParagraph0.setFontSize(12);
        flavonActiveParagraph0.setOrderIndex(1);
        flavonActiveParagraph0.setParent(flavonActiveContainer);
        flavonActiveParagraph0 = contentRepository.save(flavonActiveParagraph0);

        ImageContentEntity flavonActiveImage0 = new ImageContentEntity();
        flavonActiveImage0.setContent("/data/flavon_active.png");
        flavonActiveImage0.setHeight(300);
        flavonActiveImage0.setOrderIndex(2);
        flavonActiveImage0.setParent(flavonActiveContainer);
        flavonActiveImage0.setWidth(300);
        flavonActiveImage0 = contentRepository.save(flavonActiveImage0);
        
        ContainerContentEntity flavonEndActiveContainer = new ContainerContentEntity();
        flavonEndActiveContainer.setContentType(ContentType.ASSEMBLED);
        flavonEndActiveContainer.setDraft(false);
        flavonEndActiveContainer.setNode(heglasNode);
        flavonEndActiveContainer.setPublicIndicator(true);
        flavonEndActiveContainer.setTitle("FLAVON ACTIVE (EN)");
        flavonEndActiveContainer = containerContentRepository.save(flavonEndActiveContainer);

        TextContentEntity flavonEndActiveParagraph0 = new TextContentEntity();
        flavonEndActiveParagraph0.setContent("If we want to stay on top, to meet the expectations and face the challenges of the 21st century, to keep up with the accelerated pace of the world, we need to live a conscious and active life. This challenge affects all of us.");
        flavonEndActiveParagraph0.setFontSize(12);
        flavonEndActiveParagraph0.setOrderIndex(1);
        flavonEndActiveParagraph0.setParent(flavonEndActiveContainer);
        flavonEndActiveParagraph0 = contentRepository.save(flavonEndActiveParagraph0);

        ImageContentEntity flavonEndActiveImage0 = new ImageContentEntity();
        flavonEndActiveImage0.setContent("/data/flavon_active.png");
        flavonEndActiveImage0.setHeight(300);
        flavonEndActiveImage0.setOrderIndex(2);
        flavonEndActiveImage0.setParent(flavonEndActiveContainer);
        flavonEndActiveImage0.setWidth(300);
        flavonEndActiveImage0 = contentRepository.save(flavonEndActiveImage0);
    }

}
