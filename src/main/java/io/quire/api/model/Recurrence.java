package io.quire.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class Recurrence {

    @ApiModelProperty(
        value = "Recurrence frequency. One of: `daily`, `weekly`, `monthly`, `yearly`.",
        example = "weekly"
    )
    public String getFreq() { return null; }

    @ApiModelProperty(
        value = "Interval between occurrences. For example, `2` with `weekly` means every 2 weeks. Default: 1.",
        example = "1"
    )
    public int getInterval() { return 0; }

    @ApiModelProperty(
        value = "End date for the recurrence (UTC, ISO 8601). If not specified, the series never ends.",
        example = "2020-12-22",
        position = 4
    )
    public String getUntil() { return null; }

    @ApiModelProperty(
        value = "Days of the week to apply the recurrence. Integers where 0=Mon, 1=Tue, …, 6=Sun. "
              + "For weekly recurrences, provide a list (e.g., `[1]`, `[0,3]`). "
              + "When provided, these days define when the recurrence occurs.",
        example = "[1,3]"
    )
    public List<Integer> getByweekday() { return null; }

    @ApiModelProperty(
        value = "Week number (1-based) used with `monthly` or `yearly` frequencies.",
        example = "2"
    )
    public int getByweekno() { return 0; }

    @ApiModelProperty(
        value = "Day number (1-based) used with `monthly` or `yearly` frequencies. "
              + "Note: `byweekday` and `bydayno` cannot be specified at the same time.",
        example = "25"
    )
    public int getBydayno() { return 0; }

    @ApiModelProperty(
        value = "Month number (1–12) used with `yearly` frequency.",
        example = "10"
    )
    public int getBymonth() { return 0; }

    @ApiModelProperty(
        value = "Whether to duplicate subtasks when the task is completed. Default: true.",
        example = "false"
    )
    public boolean getDupsubtasks() { return true; }

    @ApiModelProperty(
        value = "Whether to repeat relative to the last completion date (daily frequency only). Default: false.",
        example = "true"
    )
    public boolean getSincelatest() { return false; }
}
