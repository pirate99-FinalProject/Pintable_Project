package com.example.pirate99_final.store.service;

import com.example.pirate99_final.global.MsgResponseDto;
import com.example.pirate99_final.global.exception.CustomException;
import com.example.pirate99_final.global.exception.ErrorCode;
import com.example.pirate99_final.global.exception.SuccessCode;
import com.example.pirate99_final.store.config.SearchCondition;
import com.example.pirate99_final.store.dto.ConfirmRequestDto;
import com.example.pirate99_final.store.dto.StoreRequestDto;
import com.example.pirate99_final.store.dto.StoreStatusResponseDto;
import com.example.pirate99_final.store.dto.*;
import com.example.pirate99_final.store.entity.Store;
import com.example.pirate99_final.store.entity.StoreDocument;
import com.example.pirate99_final.store.entity.StoreStatus;
import com.example.pirate99_final.store.repository.*;
import com.example.pirate99_final.user.entity.User;
import com.example.pirate99_final.user.repository.UserRepository;
import com.example.pirate99_final.waiting.entity.Waiting;
import com.example.pirate99_final.waiting.repository.WaitingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.util.StringUtils;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import static com.example.pirate99_final.global.exception.SuccessCode.*;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;                      // store repo connect
    private final StoreStatusRepository storeStatusRepository;          // storeStatus repo connect

    private final WaitingRepository waitingRepository;                  // waiting repo connect

    private final UserRepository userRepository;                        // user repo connect

    private final StoreRepositoryImpl storeRepositoryImpl;                                                              // query Dsl Repository ????????? ??????
    private final JavaMailSender emailSender;                                 // email sender

    private final StoreSearchRepository storeSearchRepository;

    private final SpringTemplateEngine templateEngine;


    // Store Create function
    public MsgResponseDto createStore(StoreRequestDto requestDto) {
        // 1. create store and storeStatus Object, insert DB
        Store store = new Store(requestDto);                                                                // DTO -> Entity
        StoreStatus storestatus = new StoreStatus(store);

        // 2. DB save
        storeRepository.save(store);                                                                        // DB Save
        storeStatusRepository.save(storestatus);

        return new MsgResponseDto(CREATE_STORE);
    }

    // Get memos from DB (all)

    public List<StoreStatusResponseDto> getStores() {
        // 1. Select All Memo
        List<StoreStatus> ListStoreStatus = storeStatusRepository.findAllBy();                          // Select All

        List<StoreStatusResponseDto> ListResponseDto = new ArrayList<>();

        for (StoreStatus storeStatus : ListStoreStatus) {
            ListResponseDto.add(new StoreStatusResponseDto(storeStatus));
        }
        return ListResponseDto;
    }

    // Get store from DB (one)
    public StoreStatusResponseDto getStore(long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
        );

        StoreStatus storeStatus = storeStatusRepository.findByStore(store);

        StoreStatusResponseDto responseDto = new StoreStatusResponseDto(storeStatus);

        return responseDto;
    }

    // DB delete function (data delete)

    public MsgResponseDto deleteStore(Long storeId) {

        Store store = storeRepository.findById(storeId).orElseThrow(                                             // find store
                () -> new CustomException(ErrorCode.NOT_FOUND_ID_ERROR)
        );

        storeStatusRepository.deleteByStore(store);                                     // ?????? ?????? ????????? ??????
        storeRepository.deleteById(storeId);                                                          // ?????? ?????? ??????

        return new MsgResponseDto(DELETE_STORE);
    }

     public MsgResponseDto enterStore(Long storeId) {        // need to update
        int availableCnt   =   0;                                                       // ?????? ?????? ??????

        // 1. find store
        Store store = storeRepository.findById(storeId).orElseThrow(()->
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
        );

        // 2. storeStatus check
        StoreStatus storeStatus = storeStatusRepository.findByStore(store);

        // 3. counting availableCnt
        if((storeStatus.getAvailableTableCnt() - 1) > 0){
            availableCnt = storeStatus.getAvailableTableCnt() - 1;
        }
        else if(storeStatus.getAvailableTableCnt() == 0){
            return new MsgResponseDto(SuccessCode.NOT_ENOUGH_TABLE);          // ?????? ?????? ?????? ??????
        }

        // 4. update storeStatus
        storeStatus.update(availableCnt);
        storeStatusRepository.save(storeStatus);

        return new MsgResponseDto(SuccessCode.CONFIRM_ENTER);
    }

    // Leave people from store
    @Transactional
    public MsgResponseDto leaveStore(Long storeId) {
        int availableCnt = 0;                                                       // ?????? ?????? ??????

        // 1. find store
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
        );

        // 2. storeStatus check
        StoreStatus storeStatus = storeStatusRepository.findByStore(store);

        availableCnt = storeStatus.getAvailableTableCnt() + 1;

        // Leaving people check
        storeStatus.update(availableCnt);

        Waiting waiting = waitingRepository.findFirstByStoreStatusAndWaitingStatusOrderByWaitingIdAsc(storeStatus, 0);

        if(waiting != null){
            // 1. find store
            store = storeRepository.findById(storeId).orElseThrow(() ->
                    new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
            );

            // 2. storeStatus check
            storeStatus = storeStatusRepository.findByStore(store);

            // 3. User find
            User user = userRepository.findByUsername(waiting.getUser().getUsername()).orElseThrow(() ->
                    new CustomException(ErrorCode.NOT_FOUND_USER_ERROR)
            );

            Waiting waiting2 = waitingRepository.findByStoreStatusAndUser(storeStatus, user);
            waiting.update(2);

            int waitingCnt = 0;

            if(storeStatus.getWaitingCnt() > 0){
                waitingCnt = storeStatus.getWaitingCnt() - 1;
            }

            availableCnt = storeStatus.getAvailableTableCnt();

            if (availableCnt > 0) {
                availableCnt = availableCnt - 1;
            }

            storeStatus.update_waitingCnt(waitingCnt, availableCnt);
        }
        return new MsgResponseDto(SuccessCode.CONFIRM_LEAVE);
    }

    @Transactional
    public MsgResponseDto confirmStore(Long storeId, ConfirmRequestDto requestDto) {
        // 1. find store
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
        );

        // 2. storeStatus check
        StoreStatus storeStatus = storeStatusRepository.findByStore(store);

        // 3. User find
        User user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND_USER_ERROR)
        );

        Waiting waiting = waitingRepository.findByStoreStatusAndUser(storeStatus, user);

        if (requestDto.getWaitingStatus() == 1) {
            waiting.update(2);
            int waitingCnt = storeStatus.getWaitingCnt() - 1;
            int availableCnt = storeStatus.getAvailableTableCnt();

            if (availableCnt > 0) {
                availableCnt = availableCnt - 1;
            }

            storeStatus.update_waitingCnt(waitingCnt, availableCnt);
        } else if (requestDto.getWaitingStatus() == 3) {
            waiting.update(3);
            int waitingCnt = storeStatus.getWaitingCnt() - 1;
            int availableCnt = storeStatus.getAvailableTableCnt();

            storeStatus.update_waitingCnt(waitingCnt, availableCnt);
        }

        return new MsgResponseDto(SuccessCode.CONFIRM_ENTER);
    }

    // call people
    @Transactional
    public MsgResponseDto callpeople(Long storeId, ConfirmRequestDto requestDto) throws MessagingException {
        // 1. find store
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
        );

        // 2. storeStatus check
        StoreStatus storeStatus = storeStatusRepository.findByStore(store);

        // 3. User find
        User user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND_USER_ERROR)
        );

        Waiting waiting = waitingRepository.findByStoreStatusAndUser(storeStatus, user);
        waiting.update(1);

        String toEmail = user.getAddress();
        String storeName = store.getStoreName();
        String title = "Pin Table ???????????? [" + storeName + "] ?????? ??????";
        String mailForm = "mailForm_call";

        MimeMessage emailForm = createEmailForm(toEmail, title, mailForm);
        emailSender.send(emailForm);
        return new MsgResponseDto(SuccessCode.CALL_PEOPLE);
    }

    private MimeMessage createEmailForm(String sendmail, String mailTitle, String mailForm) throws MessagingException {

        String setFrom = "pintable99@gmail.com";                                                                        // application-properties??? ????????? ????????? ?????? ?????? ??????
        String toEmail = sendmail;                                                                                      // ?????????
        String title = mailTitle;                                                                                       // ??????

        MimeMessage message = emailSender.createMimeMessage();                                                          // JavaMailSender??? ????????? ????????? ??????
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail); //?????? ????????? ??????
        message.setSubject(title);
        message.setFrom(setFrom);
        message.setText(setContext(mailForm), "utf-8", "html");

        return message;
    }

    private String setContext(String mailform) {
        Context context = new Context();
        return templateEngine.process(mailform, context); //mail.html
    }

    //Limit Waiting Count ?????? ????????? ??????
    @Transactional
    public MsgResponseDto limitWaitingCnt(Long storeId, LimitWaitingCntRequestDto requestDto) throws MessagingException {

        int totalWaitingCnt, limitWaitingCnt = 0;


        // 1. find store
        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
        );

        // 2. storeStatus check
        StoreStatus storeStatus = storeStatusRepository.findByStore(store);

        // ????????? ??? ???????????? '?????????', '????????????'??? ???????????? ????????????????????? ???????????? ???????????? ??????
        List<Waiting> waitingList = waitingRepository.
                findAllByStoreStatusAndWaitingStatusOrWaitingStatusOrderByWaitingIdAsc(storeStatus, 0, 1);

        // ???????????? ????????? ?????? ?????? ????????? ?????? ?????? ????????? ???????????? ???
        storeStatus.update_limitWaitingCnt(requestDto.getLimitWaitingCnt());

        limitWaitingCnt = storeStatus.getLimitWaitingCnt();


        // ???????????? ????????? '0'??? ?????? 'default' ?????? ???????????? ??????
        if (limitWaitingCnt == 1000) {

            return new MsgResponseDto(LIMIT_DEFAULT);
            // ???????????? ????????? ??? ???????????? ??? ?????? ??? ??????, ????????? ?????????????????? ????????? ??????
        } else if (limitWaitingCnt >= storeStatus.getWaitingCnt()) {

            return new MsgResponseDto(SuccessCode.LIMIT_SETTING);
            // ???????????? ????????? ??? ???????????? ??? ?????? ?????? ??????, ????????? ????????? ?????? ????????????????????? ???????????? ????????? ???????????? ????????????.
        } else if (limitWaitingCnt < storeStatus.getWaitingCnt()) {

            for (int i = limitWaitingCnt; i < storeStatus.getWaitingCnt(); i++) {

                Waiting waiting_person = waitingList.get(i);

                String toEmail = waiting_person.getUser().getAddress();
                String storeName = store.getStoreName();
                String title = "Pin Table [" + storeName + "] ????????? ?????? ??????";
                String mailForm = "mailForm_cancle";

                // ???????????? ????????? ??????
                waiting_person.update(3);


                MimeMessage emailForm = createEmailForm(toEmail, title, mailForm);
                emailSender.send(emailForm);

            return new MsgResponseDto(CONFIRM_ENTER);


            }

            return new MsgResponseDto(ErrorCode.WRONG_LIMIT_WAITING_ERROR);
        }
        return null;
    }

    public void searchCurrentMap(Model model, String latitude, String longitude, String storeName) {

        List<Store> naverList = storeRepository.searchCurrent(latitude, longitude, storeName);                           // 1. ???????????? ??????, ??????, ?????? ???????????? DB??? ????????????.
        model.addAttribute("searchList", naverList);                                                         // 2. index.html??? ????????? ?????? ??????
    }


    public void DynamicSQL(Model model, SearchCondition condition, String select) {
        List<QuerydslDto> DynamicSQL = storeRepositoryImpl.DynamicSQL(condition, select);
        model.addAttribute("searchList", DynamicSQL);
    }

    public void elasticSearch(Model model, SearchCondition condition) {
        if (!(StringUtils.isEmpty(condition.getStoreName()))) {
            List<StoreDocument> storeDocument =
                    storeSearchRepository.findTop10ByStoreName(condition.getStoreName());

            List<ESStoreResponseDto> listESStoreResponseDto = new ArrayList<>();

            for (StoreDocument storeDocumentObject : storeDocument) {
                StoreStatus storeStatus = storeStatusRepository.findByStoreId(storeDocumentObject.getId());
                listESStoreResponseDto.add(new ESStoreResponseDto(storeDocumentObject, storeStatus));
            }

            model.addAttribute("searchList", listESStoreResponseDto);
        }
        if (!(StringUtils.isEmpty(condition.getRoadNameAddress()))) {
            List<StoreDocument> storeDocument =
                    storeSearchRepository.findTop10ByRoadNameAddress(condition.getRoadNameAddress());

            List<ESStoreResponseDto> listESStoreResponseDto = new ArrayList<>();

            for (StoreDocument storeDocumentObject : storeDocument) {
                StoreStatus storeStatus = storeStatusRepository.findByStoreId(storeDocumentObject.getId());
                listESStoreResponseDto.add(new ESStoreResponseDto(storeDocumentObject, storeStatus));
            }

            model.addAttribute("searchList", listESStoreResponseDto);
        }
        if (!(StringUtils.isEmpty(condition.getTypeOfBusiness()))) {
            List<StoreDocument> storeDocument =
                    storeSearchRepository.findTop10ByTypeOfBusiness(condition.getTypeOfBusiness());

            List<ESStoreResponseDto> listESStoreResponseDto = new ArrayList<>();

            for (StoreDocument storeDocumentObject : storeDocument) {
                StoreStatus storeStatus = storeStatusRepository.findByStoreId(storeDocumentObject.getId());
                listESStoreResponseDto.add(new ESStoreResponseDto(storeDocumentObject, storeStatus));
            }

            model.addAttribute("searchList", listESStoreResponseDto);
        }
    }
    public void elasticSearchStarScore(Model model, SearchCondition condition) {
        double min = 4;
        double max = 5;

        if (!(StringUtils.isEmpty(condition.getStoreName()))) {
            List<StoreDocument> storeDocument =
                    storeSearchRepository.findTop10ByStoreNameAndStarScoreBetween(condition.getStoreName(), min, max);

            List<ESStoreResponseDto> listESStoreResponseDto = new ArrayList<>();

            for (StoreDocument storeDocumentObject : storeDocument) {
                StoreStatus storeStatus = storeStatusRepository.findByStoreId(storeDocumentObject.getId());
                listESStoreResponseDto.add(new ESStoreResponseDto(storeDocumentObject, storeStatus));
            }

            model.addAttribute("searchList", listESStoreResponseDto);
        }

        if (!(StringUtils.isEmpty(condition.getRoadNameAddress()))) {
            List<StoreDocument> storeDocument =
                    storeSearchRepository.findTop10ByRoadNameAddressAndStarScoreBetween(condition.getRoadNameAddress(), min, max);

            List<ESStoreResponseDto> listESStoreResponseDto = new ArrayList<>();

            for (StoreDocument storeDocumentObject : storeDocument) {
                StoreStatus storeStatus = storeStatusRepository.findByStoreId(storeDocumentObject.getId());
                listESStoreResponseDto.add(new ESStoreResponseDto(storeDocumentObject, storeStatus));
            }

            model.addAttribute("searchList", listESStoreResponseDto);
        }

        if (!(StringUtils.isEmpty(condition.getTypeOfBusiness()))) {
            List<StoreDocument> storeDocument =
                    storeSearchRepository.findTop10ByTypeOfBusinessAndStarScoreBetween(condition.getTypeOfBusiness(), min, max);

            List<ESStoreResponseDto> listESStoreResponseDto = new ArrayList<>();

            for (StoreDocument storeDocumentObject : storeDocument) {
                StoreStatus storeStatus = storeStatusRepository.findByStoreId(storeDocumentObject.getId());
                listESStoreResponseDto.add(new ESStoreResponseDto(storeDocumentObject, storeStatus));
            }

            model.addAttribute("searchList", listESStoreResponseDto);
        }
    }
    public void elasticSearchReview(Model model, SearchCondition condition) {
        int min = 1000;
        int max = 100000;

        if (!(StringUtils.isEmpty(condition.getStoreName()))) {
            List<StoreDocument> storeDocument =
                    storeSearchRepository.findTop10ByStoreNameAndReviewCntBetween(condition.getStoreName(), min, max);

            List<ESStoreResponseDto> listESStoreResponseDto = new ArrayList<>();

            for (StoreDocument storeDocumentObject : storeDocument) {
                StoreStatus storeStatus = storeStatusRepository.findByStoreId(storeDocumentObject.getId());
                listESStoreResponseDto.add(new ESStoreResponseDto(storeDocumentObject, storeStatus));
            }

            model.addAttribute("searchList", listESStoreResponseDto);
        }

        if (!(StringUtils.isEmpty(condition.getRoadNameAddress()))) {
            List<StoreDocument> storeDocument =
                    storeSearchRepository.findTop10ByRoadNameAddressAndReviewCntBetween(condition.getRoadNameAddress(), min, max);

            List<ESStoreResponseDto> listESStoreResponseDto = new ArrayList<>();

            for (StoreDocument storeDocumentObject : storeDocument) {
                StoreStatus storeStatus = storeStatusRepository.findByStoreId(storeDocumentObject.getId());
                listESStoreResponseDto.add(new ESStoreResponseDto(storeDocumentObject, storeStatus));
            }

            model.addAttribute("searchList", listESStoreResponseDto);
        }

        if (!(StringUtils.isEmpty(condition.getTypeOfBusiness()))) {
            List<StoreDocument> storeDocument =
                    storeSearchRepository.findTop10ByTypeOfBusinessAndReviewCntBetween(condition.getTypeOfBusiness(), min, max);

            List<ESStoreResponseDto> listESStoreResponseDto = new ArrayList<>();

            for (StoreDocument storeDocumentObject : storeDocument) {
                StoreStatus storeStatus = storeStatusRepository.findByStoreId(storeDocumentObject.getId());
                listESStoreResponseDto.add(new ESStoreResponseDto(storeDocumentObject, storeStatus));
            }

            model.addAttribute("searchList", listESStoreResponseDto);
        }
    }
    public void elasticSearchStarScoreDESC(Model model, SearchCondition condition) {
        if (!(StringUtils.isEmpty(condition.getStoreName()))) {
            List<StoreDocument> storeDocument =
                    storeSearchRepository.findTop10ByStoreNameOrderByStarScoreDesc(condition.getStoreName());

            List<ESStoreResponseDto> listESStoreResponseDto = new ArrayList<>();

            for (StoreDocument storeDocumentObject : storeDocument) {
                StoreStatus storeStatus = storeStatusRepository.findByStoreId(storeDocumentObject.getId());
                listESStoreResponseDto.add(new ESStoreResponseDto(storeDocumentObject, storeStatus));
            }

            model.addAttribute("searchList", listESStoreResponseDto);
        }

        if (!(StringUtils.isEmpty(condition.getRoadNameAddress()))) {
            List<StoreDocument> storeDocument =
                    storeSearchRepository.findTop10ByRoadNameAddressOrderByStarScoreDesc(condition.getRoadNameAddress());

            List<ESStoreResponseDto> listESStoreResponseDto = new ArrayList<>();

            for (StoreDocument storeDocumentObject : storeDocument) {
                StoreStatus storeStatus = storeStatusRepository.findByStoreId(storeDocumentObject.getId());
                listESStoreResponseDto.add(new ESStoreResponseDto(storeDocumentObject, storeStatus));
            }
            model.addAttribute("searchList", listESStoreResponseDto);
        }

        if (!(StringUtils.isEmpty(condition.getTypeOfBusiness()))) {
            List<StoreDocument> storeDocument =
                    storeSearchRepository.findTop10ByTypeOfBusinessOrderByStarScoreDesc(condition.getTypeOfBusiness());

            List<ESStoreResponseDto> listESStoreResponseDto = new ArrayList<>();

            for (StoreDocument storeDocumentObject : storeDocument) {
                StoreStatus storeStatus = storeStatusRepository.findByStoreId(storeDocumentObject.getId());
                listESStoreResponseDto.add(new ESStoreResponseDto(storeDocumentObject, storeStatus));
            }

            model.addAttribute("searchList", listESStoreResponseDto);
        }
    }
    public void elasticSearchReviewDESC(Model model, SearchCondition condition) {
        if (!(StringUtils.isEmpty(condition.getStoreName()))) {
            List<StoreDocument> storeDocument =
                    storeSearchRepository.findTop10ByStoreNameOrderByReviewCntDesc(condition.getStoreName());

            List<ESStoreResponseDto> listESStoreResponseDto = new ArrayList<>();

            for (StoreDocument storeDocumentObject : storeDocument) {
                StoreStatus storeStatus = storeStatusRepository.findByStoreId(storeDocumentObject.getId());
                listESStoreResponseDto.add(new ESStoreResponseDto(storeDocumentObject, storeStatus));
            }

            model.addAttribute("searchList", listESStoreResponseDto);
        }

        if (!(StringUtils.isEmpty(condition.getRoadNameAddress()))) {
            List<StoreDocument> storeDocument =
                    storeSearchRepository.findTop10ByRoadNameAddressOrderByReviewCntDesc(condition.getRoadNameAddress());

            List<ESStoreResponseDto> listESStoreResponseDto = new ArrayList<>();

            for (StoreDocument storeDocumentObject : storeDocument) {
                StoreStatus storeStatus = storeStatusRepository.findByStoreId(storeDocumentObject.getId());
                listESStoreResponseDto.add(new ESStoreResponseDto(storeDocumentObject, storeStatus));
            }

            model.addAttribute("searchList", listESStoreResponseDto);
        }

        if (!(StringUtils.isEmpty(condition.getTypeOfBusiness()))) {
            List<StoreDocument> storeDocument =
                    storeSearchRepository.findTop10ByTypeOfBusinessOrderByReviewCntDesc(condition.getTypeOfBusiness());

            List<ESStoreResponseDto> listESStoreResponseDto = new ArrayList<>();

            for (StoreDocument storeDocumentObject : storeDocument) {
                StoreStatus storeStatus = storeStatusRepository.findByStoreId(storeDocumentObject.getId());
                listESStoreResponseDto.add(new ESStoreResponseDto(storeDocumentObject, storeStatus));
            }

            model.addAttribute("searchList", listESStoreResponseDto);
        }
    }


    public StoreResponseDto getStoreAdminInfo(Long storeId) {

        Store store = storeRepository.findById(storeId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR));

        StoreStatus storeStatus = storeStatusRepository.findByStore(store);

        List<Waiting> waitingTeams = waitingRepository.findAllByStoreStatusAndWaitingStatusOrWaitingStatusOrderByWaitingIdAsc(storeStatus, 0, 1);

        int numberOfTeamsWaiting = waitingTeams.size();

        int numberOfCustomersInUse = storeStatus.getTotalTableCnt() - storeStatus.getAvailableTableCnt();

        return new StoreResponseDto(store, numberOfTeamsWaiting, numberOfCustomersInUse);
    }
}
