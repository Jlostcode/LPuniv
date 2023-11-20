package com.project.lpuniv.dayoung.user.signUp.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ListPage {
    private int total;
    private int currentPage;
    private List<ListDto> content;
    private int totalPages;
    private int startPage;
    private int endPage;


    public ListPage(int total,int currentPage, int size, List<ListDto> content){
        this.total = total;
        this.currentPage= currentPage;
        this.content = content;

        if (total == 0) {
            totalPages = 0;
            startPage = 0;
            endPage = 0;
        } else {
            totalPages = total / size;
            if (total % size > 0) {
                totalPages++;
            }

            int modVal = currentPage % 5;
            startPage = currentPage / 5 * 5 + 1;
            if (modVal == 0) startPage -= 5;

            endPage = startPage + 4;
            if (endPage > totalPages) endPage = totalPages;
        }
    }
    public int getTotal(){
        return total;}


    public boolean hasNoUserDto() {
        return total == 0;
    }

    public boolean hasUserDto() {
        return total > 0;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<ListDto> getContent() {
        return content;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }

}

